package org.unibl.etf.sni.anonymouschat.config.security;

import io.jsonwebtoken.Header;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class LoginAttemptService {

    private static final int MaxAttempt = 5;
    private static final String HeaderName = "X-Forwarded-For";
    private final LoadingCache<String, Integer> attemptsCache = CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.DAYS).build(new CacheLoader<String, Integer>() {
        @Override
        public Integer load(final String key) {
            return 0;
        }
    });
    private final HttpServletRequest request;

    public void loginFailed(final String key) {
        int attempts;
        try {
            attempts = attemptsCache.get(key);
        } catch (final ExecutionException e) {
            attempts = 0;
        }
        attempts++;
        attemptsCache.put(key, attempts);
    }

    public boolean isBlocked() {
        try {
            return attemptsCache.get(getClientIp()) >= MaxAttempt;
        } catch (final ExecutionException e) {
            return false;
        }
    }

    private String getClientIp() {
        final String xfHeader = request.getHeader(HeaderName);
        if (xfHeader != null) {
            return xfHeader.split(",")[0];
        }
        return request.getRemoteAddr();
    }

}

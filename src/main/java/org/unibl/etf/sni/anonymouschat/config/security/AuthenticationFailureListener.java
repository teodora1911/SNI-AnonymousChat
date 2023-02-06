package org.unibl.etf.sni.anonymouschat.config.security;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

    private static final String HeaderName = "X-Forwarded-For";

    private final HttpServletRequest request;
    private final LoginAttemptService loginAttemptService;

    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
        final String xfHeader = request.getHeader(HeaderName);
        if(xfHeader == null || xfHeader.isEmpty() || !xfHeader.contains(request.getRemoteAddr())){
            loginAttemptService.loginFailed(request.getRemoteAddr());
            System.out.println("Login failed in IF branch.");
        } else {
            loginAttemptService.loginFailed(xfHeader.split(",")[0]);
            System.out.println("Login failed in ELSE branch.");
        }
    }
}

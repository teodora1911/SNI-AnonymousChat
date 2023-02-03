package org.unibl.etf.sni.anonymouschat.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;
import org.unibl.etf.sni.anonymouschat.models.dtos.JwtUser;
import org.unibl.etf.sni.anonymouschat.utils.JwtUtils;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String AuthorizationHeaderName = "Authorization";
    private static final String AuthorizationHeaderPrefix = "Bearer ";

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        final String authorizationHeader = request.getHeader(AuthorizationHeaderName);
        if(authorizationHeader == null || !authorizationHeader.startsWith(AuthorizationHeaderPrefix)){
            filterChain.doFilter(request, response);
            return;
        }

//        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
//        if(csrfToken != null){
//            Cookie cookie = WebUtils.getCookie(request, "XSRF-TOKEN");
//            String token = csrfToken.getToken();
//            if (cookie == null || token != null && !token.equals(cookie.getValue())) {
//                cookie = new Cookie("XSRF-TOKEN", token);
//                cookie.setPath("/");
//                response.addCookie(cookie);
//            }
//        }

        final String token = authorizationHeader.replace(AuthorizationHeaderPrefix, "");
        try{
            JwtUser jwtUser = JwtUtils.getInstance().extractUserDetailsFrom(token);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(jwtUser, null, jwtUser.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch(Exception e){
            e.printStackTrace();
        }

        filterChain.doFilter(request, response);
    }

    /*
        String authorizationHeader = httpServletRequest.getHeader(authorizationHeaderName);
        if (authorizationHeader == null || !authorizationHeader.startsWith(authorizationHeaderPrefix)) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        String token = authorizationHeader.replace(authorizationHeaderPrefix, "");
        try {
            Claims claims = JwtUtility.getInstance().parseJwt(token);
            JwtUser jwtUser = new JwtUser(Integer.valueOf(claims.getId()), claims.getSubject(), null, Role.valueOf(claims.get("role", String.class)));
            Authentication authentication = new UsernamePasswordAuthenticationToken(jwtUser, null, jwtUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            logger.error("JWT Authentication failed from: " + httpServletRequest.getRemoteHost());
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    */
}

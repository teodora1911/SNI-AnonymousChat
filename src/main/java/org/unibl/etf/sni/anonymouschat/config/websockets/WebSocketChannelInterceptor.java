package org.unibl.etf.sni.anonymouschat.config.websockets;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.unibl.etf.sni.anonymouschat.models.dtos.JwtUser;
import org.unibl.etf.sni.anonymouschat.repos.ActiveParticipantsRepository;
import org.unibl.etf.sni.anonymouschat.utils.JwtUtils;

import java.security.Principal;
import java.util.List;
import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
public class WebSocketChannelInterceptor implements ChannelInterceptor {

    private static final String AuthorizationHeaderName = "Authorization";
    private static final String AuthorizationHeaderPrefix = "Bearer ";

    private final ActiveParticipantsRepository activeParticipantsRepository;

    @Override
    public void afterReceiveCompletion(Message<?> message, MessageChannel channel, Exception e){
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (accessor == null || accessor.getCommand() == null)
            return;

        MessageHeaders headers = message.getHeaders();
        String sessionId = SimpMessageHeaderAccessor.getSessionId(headers);
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (accessor == null || accessor.getCommand() == null)
            return message;

        List<String> authorizationList = accessor.getNativeHeader(AuthorizationHeaderName);
        if(authorizationList == null || authorizationList.size() < 1)
            return message;

        String token = authorizationList.get(0).replace(AuthorizationHeaderPrefix, "");
        JwtUser jwtUser = JwtUtils.getInstance().extractUserDetailsFrom(token);
        Authentication authentication = new UsernamePasswordAuthenticationToken(jwtUser, null, jwtUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        accessor.setUser(authentication);
        accessor.setLeaveMutable(true);
        return MessageBuilder.createMessage(message.getPayload(), accessor.getMessageHeaders());
    }

    private void authorize(StompHeaderAccessor accessor, Consumer<Authentication> callback) {
        List<String> authorizationList = accessor.getNativeHeader(AuthorizationHeaderName);

        if (authorizationList == null || authorizationList.size() < 1) return;
        String token = authorizationList.get(0).replace(AuthorizationHeaderPrefix, "");
        JwtUser jwtUser = JwtUtils.getInstance().extractUserDetailsFrom(token);

        Authentication authentication = new UsernamePasswordAuthenticationToken(jwtUser, null, jwtUser.getAuthorities());
        accessor.setUser(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        callback.accept(authentication);
    }
}

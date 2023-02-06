package org.unibl.etf.sni.anonymouschat.config.websockets;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.unibl.etf.sni.anonymouschat.repos.ActiveParticipantsRepository;

@Configuration
@RequiredArgsConstructor
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private static final String WS = "/socket";
    private static final String USER = "/user";
    private static final String APP = "/chat";
    private static final String PART = "/participants";
    private static final String TOPIC = "/chatroom";

    private static final String QUEUE = "/queue";

    private final ActiveParticipantsRepository activeParticipantsRepository;

    @Bean
    public WebSocketHandshakeInterceptor handshakeInterceptor() {
        return new WebSocketHandshakeInterceptor();
    }

    @Bean
    public WebSocketChannelInterceptor channelInterceptor() {
        return new WebSocketChannelInterceptor(activeParticipantsRepository);
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry){
        registry.addEndpoint(WS)
               // .addInterceptors(handshakeInterceptor())
                .setAllowedOriginPatterns("http://localhost:4200")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // everything that starts with /chat will be delegated to controllers (request will forward to controller)
        registry.setApplicationDestinationPrefixes(APP);
        registry.enableSimpleBroker(TOPIC, PART, QUEUE);
        //registry.setUserDestinationPrefix(USER);
    }

//    @Override
//    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
//        registration.setMessageSizeLimit(2000000000); // default : 64 * 1024
//        registration.setSendTimeLimit(20 * 10000); // default : 10 * 10000
//        registration.setSendBufferSizeLimit(30 * 512 * 1024); // default : 512 * 1024
//        registration.setTimeToFirstMessage(99999); // Time
//    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(channelInterceptor());
    }

    @Override
    public void configureClientOutboundChannel(ChannelRegistration registration){
        registration.interceptors(channelInterceptor());
        WebSocketMessageBrokerConfigurer.super.configureClientOutboundChannel(registration);
    }
//        registration.interceptors(new ChannelInterceptor() {
//            @Override
//            public Message<?> preSend(Message<?> message, MessageChannel channel) {
//                //StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
//                StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
//                if(accessor == null || accessor.getCommand() == null)
//                    return message;
//
//                MessageHeaders headers = message.getHeaders();
//                String sessionId = SimpMessageHeaderAccessor.getSessionId(headers);
//
//                if(StompCommand.CONNECT.equals(accessor.getCommand())){
//                    // authorize
//                } else if(StompCommand.DISCONNECT.equals(accessor.getCommand())){
//                    // authorize
//                } else if(StompCommand.SUBSCRIBE.equals(accessor.getCommand())){
//                    List<String> authorizationList = accessor.getNativeHeader(AuthorizationHeaderName);
//
//                    assert authorizationList != null;
//                    assert authorizationList.size() > 0;
//
//                    String token = authorizationList.get(0).replace(AuthorizationHeaderPrefix, "");
//                    JwtUser user = JwtUtils.getInstance().extractUserDetailsFrom(token);
//                }
//                List<String> authorizationList = accessor.getNativeHeader("Authorization");
//
//                String authorizationHeader = null;
//                if(authorizationList == null || authorizationList.size() < 1) {
//                    return message;
//                } else {
//                    authorizationHeader = authorizationList.get(0);
//                    if(authorizationHeader == null) {
//                        return message;
//                    }
//                }
//
//                final String token = authorizationHeader.replace(AuthorizationHeaderPrefix, "");
//                JwtUser jwtUser = JwtUtils.getInstance().extractUserDetailsFrom(token);
//                Authentication authentication = new UsernamePasswordAuthenticationToken(jwtUser, null, jwtUser.getAuthorities());
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//
//                accessor.setLeaveMutable(true);
//                return MessageBuilder.createMessage(message.getPayload(), accessor.getMessageHeaders());
//            }
//        });
}
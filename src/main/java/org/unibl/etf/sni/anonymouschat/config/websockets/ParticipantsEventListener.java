package org.unibl.etf.sni.anonymouschat.config.websockets;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.unibl.etf.sni.anonymouschat.repos.ActiveParticipantsRepository;

import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ParticipantsEventListener {

    private static final String LoginDestination = "/chatroom/login";
    private static final String LogoutDestination = "/chatroom/logout";

    private final ActiveParticipantsRepository participantsRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @EventListener
    public void handleSessionConnected(SessionConnectEvent event){
        SimpMessageHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(event.getMessage(), SimpMessageHeaderAccessor.class);
        //SimpMessageHeaderAccessor somethingThatDoesNotWork = SimpMessageHeaderAccessor.wrap(event.getMessage());

        if(accessor == null){
            System.out.println("Accessor is null!!!");
            return;
        }

        String username = Objects.requireNonNull(accessor.getUser()).getName();
        messagingTemplate.convertAndSend(LoginDestination, username);
        participantsRepository.add(accessor.getSessionId(), username);
    }

    @EventListener
    public void handleSessionDisconnect(SessionDisconnectEvent event){
        Optional.ofNullable(participantsRepository.getSession(event.getSessionId()))
                .ifPresent(username -> {
                    messagingTemplate.convertAndSend(LogoutDestination, username);
                    participantsRepository.remove(event.getSessionId());
                });
    }
}

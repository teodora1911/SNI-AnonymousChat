package org.unibl.etf.sni.anonymouschat.config.websockets;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.unibl.etf.sni.anonymouschat.repos.ActiveParticipantsRepository;

@Component
@RequiredArgsConstructor
public class WebSocketSessionsListener {

    private final ActiveParticipantsRepository participantsRepository;
    private final SimpMessagingTemplate messagingTemplate;

//    @EventListener
//    private void handleSessionConnected(SessionConnectEvent event) {
//        SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
//        String username = headers.getUser().getName();
//
//        LoginEvent loginEvent = new LoginEvent(username);
//        messagingTemplate.convertAndSend(loginDestination, loginEvent);
//
//        // We store the session as we need to be idempotent in the disconnect event processing
//        participantRepository.add(headers.getSessionId(), loginEvent);
//    }
}

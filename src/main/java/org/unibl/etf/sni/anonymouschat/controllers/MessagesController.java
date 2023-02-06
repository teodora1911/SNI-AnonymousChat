package org.unibl.etf.sni.anonymouschat.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;
import org.unibl.etf.sni.anonymouschat.config.kafka.KafkaListeners;
import org.unibl.etf.sni.anonymouschat.models.requests.MessageRequest;
import org.unibl.etf.sni.anonymouschat.repos.ActiveParticipantsRepository;

import java.security.Principal;
import java.util.List;

@Controller
@CrossOrigin
@RequiredArgsConstructor
public class MessagesController {
    private final ActiveParticipantsRepository participantsRepository;
    private final KafkaListeners kafkaListener;
    private final ObjectMapper mapper;

    @SubscribeMapping("/participants")
    public List<String> getParticipants() {
        return participantsRepository.getUsernames();
    }

    @MessageMapping("/send/private")
    public void sendPrivateMessage(@Payload MessageRequest messageRequest, Principal principal){
        if(messageRequest.getSender().equals(principal.getName())){
            //messagingTemplate.convertAndSendToUser(messageRequest.getReceiver(), "/queue/private", messageRequest);
            try{
                kafkaListener.sendData(mapper.writeValueAsString(messageRequest), messageRequest.getSegmentId());
            } catch(Exception e){
                // TODO
                e.printStackTrace();
            }
        } else System.out.println("Principal name {" + principal.getName() + "} does not match request sender.");
    }


}

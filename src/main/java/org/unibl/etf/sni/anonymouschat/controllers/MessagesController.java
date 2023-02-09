package org.unibl.etf.sni.anonymouschat.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;

import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;
import org.unibl.etf.sni.anonymouschat.config.kafka.KafkaListeners;
import org.unibl.etf.sni.anonymouschat.models.dtos.Message;
import org.unibl.etf.sni.anonymouschat.models.dtos.MessageInfo;
import org.unibl.etf.sni.anonymouschat.models.requests.MessageRequest;
import org.unibl.etf.sni.anonymouschat.repos.ActiveParticipantsRepository;
import org.unibl.etf.sni.anonymouschat.repos.SessionKeyRepository;
import org.unibl.etf.sni.anonymouschat.utils.CryptUtils;

import java.security.Principal;
import java.util.List;

@Controller
@CrossOrigin
@RequiredArgsConstructor
public class MessagesController {
    private final ActiveParticipantsRepository participantsRepository;
    private final SessionKeyRepository sessionKeyRepository;
    private final KafkaListeners kafkaListener;
    private final ObjectMapper objectMapper;
    @Lazy private final  ModelMapper modelMapper;

    @SubscribeMapping("/participants")
    public List<String> getParticipants() {
        return participantsRepository.getUsernames();
    }

    @MessageMapping("/send/private")
    public void sendPrivateMessage(@Payload MessageRequest messageRequest, Principal principal){
        if(messageRequest.getSender().equals(principal.getName())){
            try{
                String sessionKey = sessionKeyRepository.getSessionKey(messageRequest.getSender(), messageRequest.getReceiver());

                MessageInfo messageInfo = modelMapper.map(messageRequest, MessageInfo.class);
                String encPayload = CryptUtils.getInstance().encryptSymmetric(objectMapper.writeValueAsString(messageInfo), sessionKey);
                Message message = new Message(messageRequest.getSender(), messageRequest.getReceiver(), encPayload);
                kafkaListener.sendData(objectMapper.writeValueAsString(message));
            } catch(Exception e){
                // TODO
                e.printStackTrace();
            }
        } else System.out.println("Principal name {" + principal.getName() + "} does not match request sender.");
    }
}

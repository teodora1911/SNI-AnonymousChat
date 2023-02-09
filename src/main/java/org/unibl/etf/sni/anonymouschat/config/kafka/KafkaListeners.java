package org.unibl.etf.sni.anonymouschat.config.kafka;

import static org.unibl.etf.sni.anonymouschat.utils.Constants.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.unibl.etf.sni.anonymouschat.models.dtos.Message;
import org.unibl.etf.sni.anonymouschat.models.dtos.MessageInfo;
import org.unibl.etf.sni.anonymouschat.models.requests.MessageRequest;
import org.unibl.etf.sni.anonymouschat.repos.SessionKeyRepository;
import org.unibl.etf.sni.anonymouschat.utils.CryptUtils;

import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class KafkaListeners {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final SimpMessagingTemplate messagingTemplate;
    private final SessionKeyRepository sessionKeyRepository;
    private final ObjectMapper objectMapper;
    @Lazy private final ModelMapper modelMapper;

    private int serverNo = 0;

    @KafkaListener(topics = UsersTopicName, groupId = "sni")
    void listener(String data) {
        try{
            Message message = objectMapper.readValue(data, Message.class);
            String sessionKey = sessionKeyRepository.getSessionKey(message.getSender(), message.getReceiver());
            String decPayload = CryptUtils.getInstance().decryptSymmetric(message.getPayload().getBytes(StandardCharsets.UTF_8), sessionKey);
            MessageInfo messageInfo = objectMapper.readValue(decPayload, MessageInfo.class);
            MessageRequest messageRequest = MessageRequest.builder()
                                                            .id(messageInfo.getId())
                                                            .segmentId(messageInfo.getSegmentId())
                                                            .segmentsNumber(messageInfo.getSegmentsNumber())
                                                            .content(messageInfo.getContent())
                                                            .extension(messageInfo.getExtension())
                                                            .sender(message.getSender())
                                                            .receiver(message.getReceiver()).build();
            messagingTemplate.convertAndSendToUser(messageRequest.getReceiver(), UserDestination, messageRequest);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void sendData(String data){
        kafkaTemplate.send(getServerTopicNameFrom(serverNo), data);
        ++this.serverNo;
        this.serverNo %= 4;
    }

    private String getServerTopicNameFrom(int serverNo){
        int no = serverNo % 4;

        if(no == 0)
            return FirstTopicName;
        else if(no == 1)
            return SecondTopicName;
        else if(no == 2)
            return ThirdTopicName;
        else
            return FourthTopicName;
    }

}

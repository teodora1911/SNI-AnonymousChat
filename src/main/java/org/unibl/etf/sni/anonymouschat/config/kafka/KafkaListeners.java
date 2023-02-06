package org.unibl.etf.sni.anonymouschat.config.kafka;

import static org.unibl.etf.sni.anonymouschat.utils.Constants.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.unibl.etf.sni.anonymouschat.models.requests.MessageRequest;

@Component
@RequiredArgsConstructor
public class KafkaListeners {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final SimpMessagingTemplate messagingTemplate;
    private final ObjectMapper mapper;

    @KafkaListener(topics = UsersTopicName, groupId = "sni")
    void listener(String data) {
        try{
            MessageRequest messageRequest = mapper.readValue(data, MessageRequest.class);
            messagingTemplate.convertAndSendToUser(messageRequest.getReceiver(), UserDestination, messageRequest);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void sendData(String data, int serverNo){
        kafkaTemplate.send(getServerTopicNameFrom(serverNo), data);
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

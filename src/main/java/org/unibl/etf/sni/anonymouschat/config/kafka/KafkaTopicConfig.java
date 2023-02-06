package org.unibl.etf.sni.anonymouschat.config.kafka;

import static org.unibl.etf.sni.anonymouschat.utils.Constants.*;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic usersTopic() {
        return TopicBuilder.name(UsersTopicName).build();
    }

    @Bean
    public NewTopic firstTopic() {
        return TopicBuilder.name(FirstTopicName).build();
    }

    @Bean
    public NewTopic secondTopic() {
        return TopicBuilder.name(SecondTopicName).build();
    }

    @Bean
    public NewTopic thirdTopic() {
        return TopicBuilder.name(ThirdTopicName).build();
    }

    @Bean
    public NewTopic fourthTopic() {
        return TopicBuilder.name(FourthTopicName).build();
    }

}

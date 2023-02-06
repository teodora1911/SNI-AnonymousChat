package org.unibl.etf.sni.anonymouschat.config.chat;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.unibl.etf.sni.anonymouschat.repos.ActiveParticipantsRepository;

@Configuration
public class ChatConfig {

    @Bean
    public ActiveParticipantsRepository activeParticipantsRepository() {
        return new ActiveParticipantsRepository();
    }
}

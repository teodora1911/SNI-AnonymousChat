package org.unibl.etf.sni.anonymouschat.config.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.unibl.etf.sni.anonymouschat.repos.ActiveParticipantsRepository;
import org.unibl.etf.sni.anonymouschat.repos.SessionKeyRepository;
import org.unibl.etf.sni.anonymouschat.repos.UserEntityRepository;

@Configuration
@RequiredArgsConstructor
public class ChatConfig {

    @Bean
    public ActiveParticipantsRepository activeParticipantsRepository() {
        return new ActiveParticipantsRepository();
    }

    @Bean
    public SessionKeyRepository sessionKeyRepository() {
        return new SessionKeyRepository();
    }
}

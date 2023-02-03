package org.unibl.etf.sni.anonymouschat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.security.web.SecurityFilterChain;

@SpringBootApplication//(exclude = { SecurityAutoConfiguration.class })
public class AnonymousChatApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnonymousChatApplication.class, args);
    }

}

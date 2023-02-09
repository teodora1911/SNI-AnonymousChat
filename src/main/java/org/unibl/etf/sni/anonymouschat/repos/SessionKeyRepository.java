package org.unibl.etf.sni.anonymouschat.repos;

import org.unibl.etf.sni.anonymouschat.utils.CryptUtils;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class SessionKeyRepository {

    private final List<Conversation> conversations = Collections.synchronizedList(new ArrayList<>());

    public void add(String person1, String person2){
        if(conversations.stream().anyMatch(c -> (c.getPerson1().equals(person1) || c.getPerson2().equals(person1)) && (c.getPerson1().equals(person2) || c.getPerson2().equals(person2))))
            return;

        try{
            String sessionKey = CryptUtils.getInstance().generateSessionKey();
            conversations.add(new Conversation(person1, person2, sessionKey));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public String getSessionKey(String person1, String person2){
        Optional<Conversation> conversation = conversations.stream().filter(c -> (c.getPerson1().equals(person1) || c.getPerson2().equals(person1)) && (c.getPerson1().equals(person2) || c.getPerson2().equals(person2))).findFirst();
        return conversation.map(Conversation::getSessionKey).orElseThrow();
    }

    public void remove(String person){
        // removing all session keys for that user
        conversations.removeIf(c -> c.getPerson1().equals(person) || c.getPerson2().equals(person));
    }

}

class Conversation {
    private String person1;
    private String person2;
    private String sessionKey;

    public Conversation() {}

    public Conversation(String person1, String person2, String sessionKey){
        this.person1 = person1;
        this.person2 = person2;
        this.sessionKey = sessionKey;
    }

    public String getPerson1(){ return person1; }
    public String getPerson2() { return person2; }
    public String getSessionKey() {return sessionKey; }

    public void setPerson1(String person1) { this.person1 = person1; }
    public void setPerson2(String person2) { this.person2 = person2; }
    public void setSessionKey(String sessionKey) { this.sessionKey = sessionKey; }
}

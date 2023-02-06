package org.unibl.etf.sni.anonymouschat.repos;

import java.security.Principal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ActiveParticipantsRepository {

    private Map<String, String> sessions = new ConcurrentHashMap<>();

    public void add(String sessionId, String username){
        sessions.put(sessionId, username);
    }

    public String getSession(String sessionId){
        return sessions.get(sessionId);
    }

    public void remove(String sessionId){
        sessions.remove(sessionId);
    }

    public Map<String, String> getSessions() {
        return sessions;
    }

    public void setSessions(Map<String, String> sessions){
        this.sessions = sessions;
    }

    public List<String> getUsernames(){
        return sessions.values().stream().toList();
    }
}

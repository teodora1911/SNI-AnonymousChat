package org.unibl.etf.sni.anonymouschat.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.util.HtmlUtils;
import org.unibl.etf.sni.anonymouschat.exceptions.UnauthorizedException;
import org.unibl.etf.sni.anonymouschat.models.requests.MessageRequest;
import org.unibl.etf.sni.anonymouschat.services.UserService;
import org.unibl.etf.sni.anonymouschat.utils.JwtUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/chat/my")
@RequiredArgsConstructor
//@Controller
public class MessagesController {



    private final UserService userService;

    @GetMapping("messages")
    public String dummy(){
        return "Dummy.";
    }
//
//    private HashMap<String, SseEmitter> emitters = new HashMap<>();
//    private List<SseEmitter> emitters = new ArrayList<>();
//
//    @PostMapping("/messages/new")
//    public ResponseEntity<String> sendMessage(@RequestBody @Valid MessageRequest messageRequest, @RequestHeader(name = "Authorization") String token) {
//        validateToken(token, messageRequest.getSenderId());
//        if(userService.getUsernameById(messageRequest.getSenderId()) != null) {
//            for(SseEmitter emitter : emitters) {
//                try {
//                    emitter.send(messageRequest);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            return ResponseEntity.ok("Success");
//        } else return ResponseEntity.notFound().build();
//    }
//
//    @PostMapping("/new-question")
//    public void dummyPost(String question) {
//        for(SseEmitter emitter : emitters) {
//            try {
//                emitter.send(SseEmitter.event().name("tesi").data(question));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    @RequestMapping("/messages")
//    public SseEmitter something() {
//        SseEmitter sseEmitter = new SseEmitter();
//        emitters.add(sseEmitter);
//        sseEmitter.onCompletion(() -> emitters.remove(sseEmitter));
//        return sseEmitter;
//    }


//    @GetMapping
//    public String dummy() {
//        return "Dummy.";
//    }
//
    private void validateToken(String token, Integer id){
        if(!JwtUtils.getInstance().isTokenValid(token, id))
            throw new UnauthorizedException("You are not authorized for this action or your token is expired. Please try again.");
    }

}

package org.unibl.etf.sni.anonymouschat.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.sni.anonymouschat.models.requests.LoginRequest;
import org.unibl.etf.sni.anonymouschat.models.responses.LoginResponse;
import org.unibl.etf.sni.anonymouschat.services.AuthService;

@RestController
@CrossOrigin
@RequestMapping("/chat/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody @Valid LoginRequest request) {
        return authService.login(request);
    }
}

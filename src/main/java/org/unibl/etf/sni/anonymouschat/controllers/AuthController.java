package org.unibl.etf.sni.anonymouschat.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.sni.anonymouschat.exceptions.UnauthorizedException;
import org.unibl.etf.sni.anonymouschat.models.requests.LoginRequest;
import org.unibl.etf.sni.anonymouschat.models.requests.LogoutRequest;
import org.unibl.etf.sni.anonymouschat.models.responses.LoginResponse;
import org.unibl.etf.sni.anonymouschat.services.AuthService;
import org.unibl.etf.sni.anonymouschat.utils.JwtUtils;

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

    @PostMapping("/logout")
    public void logout(@RequestBody @NotNull @Valid LogoutRequest request, @RequestHeader(name = "Authorization") String token) {
//        if(JwtUtils.getInstance().isTokenValid(token, request.getId())){
//
//        }
    }
}

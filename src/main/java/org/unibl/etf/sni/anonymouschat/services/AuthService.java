package org.unibl.etf.sni.anonymouschat.services;

import org.unibl.etf.sni.anonymouschat.models.dtos.JwtUser;
import org.unibl.etf.sni.anonymouschat.models.requests.LoginRequest;
import org.unibl.etf.sni.anonymouschat.models.requests.LogoutRequest;
import org.unibl.etf.sni.anonymouschat.models.requests.RefreshTokenRequest;
import org.unibl.etf.sni.anonymouschat.models.responses.LoginResponse;
import org.unibl.etf.sni.anonymouschat.models.responses.RefreshTokenResponse;

public interface AuthService {
    LoginResponse login(LoginRequest request);
}

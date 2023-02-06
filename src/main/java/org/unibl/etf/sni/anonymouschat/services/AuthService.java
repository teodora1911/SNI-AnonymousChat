package org.unibl.etf.sni.anonymouschat.services;

import org.unibl.etf.sni.anonymouschat.models.requests.LoginRequest;
import org.unibl.etf.sni.anonymouschat.models.responses.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest request);
}

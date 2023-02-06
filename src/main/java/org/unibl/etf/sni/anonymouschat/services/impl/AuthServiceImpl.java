package org.unibl.etf.sni.anonymouschat.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.unibl.etf.sni.anonymouschat.models.dtos.JwtUser;
import org.unibl.etf.sni.anonymouschat.models.entities.UserEntity;
import org.unibl.etf.sni.anonymouschat.models.requests.LoginRequest;
import org.unibl.etf.sni.anonymouschat.models.responses.LoginResponse;
import org.unibl.etf.sni.anonymouschat.repos.UserEntityRepository;
import org.unibl.etf.sni.anonymouschat.services.AuthService;
import org.unibl.etf.sni.anonymouschat.utils.JwtUtils;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserEntityRepository userEntityRepository;
    private final ModelMapper modelMapper;

    @Value("${authorization.token.expiration-time}")
    private String tokenExpirationTime;
    @Value("${authorization.token.secret}")
    private String tokenSecret;

    public AuthServiceImpl(AuthenticationManager authenticationManager, UserEntityRepository repository,@Lazy ModelMapper modelMapper){
        this.authenticationManager = authenticationManager;
        this.userEntityRepository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        LoginResponse response = null;
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        // user is now authenticated
        JwtUser user = (JwtUser) authentication.getPrincipal();
        Optional<UserEntity> userEntityOptional = userEntityRepository.findByUsername(user.getUsername());
        if(userEntityOptional.isPresent()){
            UserEntity userEntity = userEntityOptional.get();
            response = modelMapper.map(userEntity, LoginResponse.class);
            response.setToken(JwtUtils.getInstance().generateJwt(user));
        }
        return response;
    }

//    @Override
//    public RefreshTokenResponse refreshToken(RefreshTokenRequest request) {
//        String refreshToken = request.getRefreshToken();
//        Claims claims = JwtUtils.getInstance().parseJwt(refreshToken);
//        JwtUser user = new JwtUser(null, claims.getSubject(), null, Role.USER);
//        String jwt = JwtUtils.getInstance().generateJwt(user);
//
//        RefreshTokenResponse response = new RefreshTokenResponse();
//        response.setJwtToken(jwt);
//        return response;
//    }
//
//    @Override
//    public LoginResponse getStatus(JwtUser user){
//        LoginResponse response = null;
//        Optional<UserEntity> userEntityOptional = userEntityRepository.findByUsername(user.getUsername());
//        if(userEntityOptional.isPresent())
//            response = modelMapper.map(userEntityOptional.get(), LoginResponse.class);
//        return response;
//    }

}

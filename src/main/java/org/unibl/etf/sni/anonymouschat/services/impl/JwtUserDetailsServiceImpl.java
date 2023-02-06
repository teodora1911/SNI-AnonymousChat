package org.unibl.etf.sni.anonymouschat.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.unibl.etf.sni.anonymouschat.config.security.LoginAttemptService;
import org.unibl.etf.sni.anonymouschat.models.dtos.JwtUser;
import org.unibl.etf.sni.anonymouschat.repos.UserEntityRepository;
import org.unibl.etf.sni.anonymouschat.services.JwtUserDetailsService;

@Service
public class JwtUserDetailsServiceImpl implements JwtUserDetailsService {

    private final UserEntityRepository userEntityRepository;
    private final ModelMapper modelMapper;
    private final LoginAttemptService loginAttemptService;

    @Autowired // added
    public JwtUserDetailsServiceImpl(UserEntityRepository userEntityRepository,
                                     @Lazy ModelMapper modelMapper,
                                     LoginAttemptService loginAttemptService) {
        this.userEntityRepository = userEntityRepository;
        this.modelMapper = modelMapper;
        this.loginAttemptService = loginAttemptService;
    }

    @Override
    public JwtUser loadUserByUsername(String username) throws UsernameNotFoundException {
        if(loginAttemptService.isBlocked())
            throw new RuntimeException("Blocked!");
        return modelMapper.map(userEntityRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username)), JwtUser.class);
    }
}

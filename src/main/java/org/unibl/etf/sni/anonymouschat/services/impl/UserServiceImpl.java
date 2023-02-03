package org.unibl.etf.sni.anonymouschat.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.unibl.etf.sni.anonymouschat.repos.UserEntityRepository;
import org.unibl.etf.sni.anonymouschat.services.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserEntityRepository userEntityRepository;

    @Override
    public String getUsernameById(Integer id){
        return userEntityRepository.getUsernameById(id);
    }
}

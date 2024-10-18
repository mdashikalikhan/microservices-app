package com.gateway.user_service.service;

import com.gateway.user_service.model.UserDomainModel;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private BCryptPasswordEncoder encoder;
    @Override
    public UserDomainModel createUser(UserDomainModel user) {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(!username.equals("khan.ashik@gmail.com")){
            throw new UsernameNotFoundException(username);
        }
        return new User("Md Ashik Ali Khan", encoder.encode("123456"),
                new ArrayList<>());
    }
}

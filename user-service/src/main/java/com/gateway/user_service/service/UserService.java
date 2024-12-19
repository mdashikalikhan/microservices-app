package com.gateway.user_service.service;

import com.gateway.user_service.model.UserDomainModel;
import com.gateway.user_service.model.UserResponseModel;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDomainModel createUser(UserDomainModel user);
    UserResponseModel findByUserId(String useId);
}

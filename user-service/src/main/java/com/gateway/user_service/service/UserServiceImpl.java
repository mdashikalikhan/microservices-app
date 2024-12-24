package com.gateway.user_service.service;

import com.gateway.user_service.dao.UserRepository;
import com.gateway.user_service.entity.UserEntity;
import com.gateway.user_service.model.AlbumResponseModel;
import com.gateway.user_service.model.UserDomainModel;
import com.gateway.user_service.model.UserResponseModel;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private BCryptPasswordEncoder encoder;

    private UserRepository userRepository;

    private RestTemplate restTemplate;
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

    @Override
    public UserResponseModel findByUserId(String useId) {
        UserEntity userEntity = userRepository.findUserById(useId)
                .orElseThrow(() -> new NoSuchElementException("User not found with ID: " + useId));
        ModelMapper modelMapper = new ModelMapper();
        UserResponseModel userResponseModel = modelMapper.map(userEntity, UserResponseModel.class);

        List<AlbumResponseModel> albumResponseModelList
                restTemplate.exchange()
        return userResponseModel;
    }
}

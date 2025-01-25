package com.gateway.user_service.service;

import com.gateway.user_service.dao.UserRepository;
import com.gateway.user_service.entity.UserEntity;
import com.gateway.user_service.model.AlbumResponseModel;
import com.gateway.user_service.model.UserDomainModel;
import com.gateway.user_service.model.UserResponseModel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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
@Slf4j
public class UserServiceImpl implements UserService {

    private BCryptPasswordEncoder encoder;

    private UserRepository userRepository;

    //private RestTemplate restTemplate;

    private AlbumServiceClient albumServiceClient;

    private Environment environment;
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
    public UserResponseModel findByUserId(String userId) {
        UserEntity userEntity = userRepository.findUserById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found with ID: " + userId));
        ModelMapper modelMapper = new ModelMapper();
        UserResponseModel userResponseModel = modelMapper.map(userEntity, UserResponseModel.class);


        /*String albumUrl = String.format(environment.getProperty("albums.url"), userId);
        ResponseEntity<List<AlbumResponseModel>> listResponseEntity = restTemplate.exchange(albumUrl, HttpMethod.GET, null, new
                ParameterizedTypeReference<List<AlbumResponseModel>>(){});

        List<AlbumResponseModel> albumList = listResponseEntity.getBody();*/

        log.debug("Before calling album Microservice");

        List<AlbumResponseModel> albumList = albumServiceClient.getAlbums(userId).getBody();

        log.debug("After calling album Microservice");

        userResponseModel.setAlbums(albumList);

        return userResponseModel;
    }
}

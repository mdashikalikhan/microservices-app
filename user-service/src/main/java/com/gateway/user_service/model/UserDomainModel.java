package com.gateway.user_service.model;

import lombok.Data;

@Data
public class UserDomainModel {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}

package com.gateway.user_service.model;

import lombok.Data;

@Data
public class LoginRequestModel {

    private String email;
    private String password;
}

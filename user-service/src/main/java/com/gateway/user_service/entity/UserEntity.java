package com.gateway.user_service.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Table(name="users")
@Data
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="FIRST_NAME",nullable=false, length=50)
    private String firstName;

    @Column(name="LAST_NAME",nullable=false, length=50)
    private String lastName;

    @Column(name="EMAIL",nullable=false, length=120, unique=true)
    private String email;

    @Column(name="USER_ID",nullable=false, unique=true)
    private String userId;

    @Column(name="PASSWORD",nullable=false)
    private String encryptedPassword;
}

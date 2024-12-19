package com.gateway.user_service.dao;

import com.gateway.user_service.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query(value="select * from users where USER_ID=?1", nativeQuery = true)
    Optional<UserEntity> findUserById(String userId);
}

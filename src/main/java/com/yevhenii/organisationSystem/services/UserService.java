package com.yevhenii.organisationSystem.services;

import com.yevhenii.organisationSystem.entity.User;
import com.yevhenii.organisationSystem.security.pojo.SignUpRequest;

import java.util.Optional;

public interface UserService {

    void register(SignUpRequest signUpRequest);
    Optional<User> findByEmail(String email);
}

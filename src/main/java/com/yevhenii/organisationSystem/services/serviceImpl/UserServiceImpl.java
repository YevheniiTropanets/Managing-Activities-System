package com.yevhenii.organisationSystem.services.serviceImpl;

import com.yevhenii.organisationSystem.entity.Profile;
import com.yevhenii.organisationSystem.entity.User;
import com.yevhenii.organisationSystem.entity.enums.ERole;
import com.yevhenii.organisationSystem.repository.ProfileRepository;
import com.yevhenii.organisationSystem.repository.RoleRepository;
import com.yevhenii.organisationSystem.repository.UserRepository;
import com.yevhenii.organisationSystem.security.pojo.SignUpRequest;
import com.yevhenii.organisationSystem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    RoleRepository roleRepository;
    ProfileRepository profileRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           ProfileRepository profileRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.profileRepository  = profileRepository;
    }

    @Override
    @Transactional
    public void register(SignUpRequest signUpRequest) {
        User user = new User(signUpRequest.getEmail(), signUpRequest.getPassword(),
                Collections.singletonList(roleRepository.findByName(ERole.ROLE_USER).orElseThrow(
                                () -> new RuntimeException("Role USER dont found"))));
        Profile profile = new Profile("","","","",user);
        userRepository.save(user);
        profileRepository.save(profile);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}

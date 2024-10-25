package com.yevhenii.organisationSystem.services;


import com.yevhenii.organisationSystem.dto.ProfileDTO;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface ProfileService {
    void save(ProfileDTO ProfileDTO);

    void update(ProfileDTO profileDTO);

    Optional<ProfileDTO> findByUserId(Long userId);
}

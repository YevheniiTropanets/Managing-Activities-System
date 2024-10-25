package com.yevhenii.organisationSystem.services.serviceImpl;

import com.yevhenii.organisationSystem.controller.util.SecurityUtils;
import com.yevhenii.organisationSystem.dto.ProfileDTO;
import com.yevhenii.organisationSystem.dto.mapper.ApplicationMapper;
import com.yevhenii.organisationSystem.entity.Profile;
import com.yevhenii.organisationSystem.entity.User;
import com.yevhenii.organisationSystem.repository.ProfileRepository;
import com.yevhenii.organisationSystem.services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class ProfileServiceImpl implements ProfileService {
    ProfileRepository profileRepository;
    SecurityUtils securityUtils;
    ApplicationMapper applicationMapper;

    @Autowired
    public ProfileServiceImpl(ProfileRepository profileRepository, SecurityUtils securityUtils, ApplicationMapper applicationMapper) {
        this.profileRepository = profileRepository;
        this.securityUtils = securityUtils;
        this.applicationMapper = applicationMapper;
    }

    @Override
    public void save(ProfileDTO ProfileDTO) {

    }

    @Override
    public void update(ProfileDTO profileDTO) {
        Long userId = securityUtils.getUserId();
        Optional<Profile> profileOptional = profileRepository.findByUserId(userId);
        Profile profile = profileOptional.orElseGet(() -> {
            Profile newProfile = new Profile(profileDTO.getOrganisation(), profileDTO.getFirstname(),
                    profileDTO.getSurname(), profileDTO.getPhone(), new User(userId));
            return profileRepository.save(newProfile);
        });
        profile.setFirstname(profileDTO.getFirstname());
        profile.setSurname(profileDTO.getSurname());
        profile.setPhone(profileDTO.getPhone());
        profile.setOrganisation(profileDTO.getOrganisation());
        profile.setUser(new User(userId));
        profileRepository.save(profile);
    }

    @Override
    public Optional<ProfileDTO> findByUserId(Long userId) {
        return profileRepository
                .findByUserId(userId)
                .map(applicationMapper::profileToDTO);

    }
}

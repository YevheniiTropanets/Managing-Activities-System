package com.yevhenii.organisationSystem.services;

import com.yevhenii.organisationSystem.dto.SaveBannerDTO;
import com.yevhenii.organisationSystem.entity.ActivityBanner;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface BanerService  {

    void save(SaveBannerDTO saveBannerDTO);
    Optional<ActivityBanner> findById(Long bannerId);
    void delete(Long banerId);
    void update(SaveBannerDTO saveBannerDto, Long bannerId);
    boolean isBannerBelongToUser(Long userId, Long bannerId);

}

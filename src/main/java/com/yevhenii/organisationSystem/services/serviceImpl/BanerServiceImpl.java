package com.yevhenii.organisationSystem.services.serviceImpl;

import com.yevhenii.organisationSystem.dto.SaveBannerDTO;
import com.yevhenii.organisationSystem.dto.mapper.ApplicationMapper;
import com.yevhenii.organisationSystem.entity.Activity;
import com.yevhenii.organisationSystem.entity.ActivityBanner;
import com.yevhenii.organisationSystem.repository.ActivityRepository;
import com.yevhenii.organisationSystem.repository.BanerRepository;
import com.yevhenii.organisationSystem.services.BanerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class BanerServiceImpl implements BanerService {
    ApplicationMapper applicationMapper;
    BanerRepository banerRepository;
    ActivityRepository activityRepository;

    @Autowired
    public BanerServiceImpl(ActivityRepository activityRepository,ApplicationMapper applicationMapper, BanerRepository banerRepository) {
        this.applicationMapper = applicationMapper;
        this.banerRepository = banerRepository;
        this.activityRepository = activityRepository;
    }
    @Transactional
    @Override
    public void save(SaveBannerDTO saveBannerDTO) {
        ActivityBanner activityBanner = applicationMapper.bannerDtoToEntity(saveBannerDTO);
        Optional<Activity> activity =  activityRepository.findById(saveBannerDTO.getActivityId());
        activityBanner.setActivity(activity.get());
        banerRepository.save(activityBanner);
    }

    @Override
    public Optional<ActivityBanner> findById(Long bannerId) {
        return banerRepository.findById(bannerId);
    }

    @Override
    public void delete(Long banerId) {
        banerRepository.deleteById(banerId);
    }

    @Override
    public void update(SaveBannerDTO saveBannerDto, Long activityId) {
        Optional<ActivityBanner> activityBanner = banerRepository.findByActivityId(activityId);
        ActivityBanner activityBanner1 = activityBanner.get();
        activityBanner1.setDescription(saveBannerDto.getDescription());
        activityBanner1.setImage(saveBannerDto.getImage());
        banerRepository.save(activityBanner1);
    }

    @Override
    public boolean isBannerBelongToUser(Long userId, Long bannerId) {
        return false;
    }
}

package com.yevhenii.organisationSystem.services;

import com.yevhenii.organisationSystem.dto.ActivityDTO;
import com.yevhenii.organisationSystem.dto.SaveActivityDTO;
import com.yevhenii.organisationSystem.entity.Activity;

import java.util.List;
import java.util.Optional;

public interface ActivityService {
    void save(SaveActivityDTO saveActivityDTO);
    boolean isActivityBelongToUser(Long userId,Long activityId);
    void delete(Long activityId);
    boolean isActivityBelongToUserByTitle(Long userId,String activityTitle);
    List<ActivityDTO> findAllByUserId(Long userId);
    Optional<Activity> findById(Long activityId);
    void update(SaveActivityDTO saveActivityDTO,Long activityId);

    boolean doesActivityHaveBanner(Long activityId);
}

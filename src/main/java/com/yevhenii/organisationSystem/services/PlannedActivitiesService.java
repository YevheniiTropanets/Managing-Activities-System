package com.yevhenii.organisationSystem.services;

import com.yevhenii.organisationSystem.entity.PlannedActivities;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PlannedActivitiesService {
    List<PlannedActivities> getPlannedActivitiesByVenue(Long venueId);

    List<PlannedActivities> findAllForToday();

    List<PlannedActivities> getPlannedActivities(Long activityId);
}

package com.yevhenii.organisationSystem.repository;

import com.yevhenii.organisationSystem.entity.PlannedActivities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface PlannedActivityRepository extends JpaRepository<PlannedActivities,Long> {

    @Query("select p from PlannedActivities p where p.activity.id = :activityId")
    Optional<PlannedActivities> findByActivityId(Long activityId);

    @Query("select p from PlannedActivities p where p.activity.id = :activityId and p.startDate = :date")
    Optional<PlannedActivities> findByActivityIdAndDate(Long activityId, Timestamp date);


    @Query("SELECT p FROM PlannedActivities p WHERE p.venue.id = :venueId AND DATE(p.startDate) >= CURRENT_DATE")
    List<PlannedActivities> getPlannedActivitiesByVenue(Long venueId);
    @Query("SELECT  p from PlannedActivities  p WHERE DATE(p.startDate) >= current_date")
    List<PlannedActivities> findAllForToday();

    @Query("SELECT p FROM PlannedActivities p WHERE p.activity.id = :activityId AND DATE(p.startDate) >= CURRENT_DATE")
    List<PlannedActivities> getPlannedActivities(Long activityId);
}

package com.yevhenii.organisationSystem.repository;

import com.yevhenii.organisationSystem.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ActivityRepository extends JpaRepository<Activity,Long> {
    @Query("select count(a) > 0 from Activity  a where a.id  =:activityId and a.user.id =:userId")
    boolean isActivityBelongToUser(Long userId,Long activityId);
    void deleteById(Long activityId);
    @Query("select a from Activity  a where a.title = :activityTitle and a.user.id =:userId")
    Activity findByActivityTitle(String activityTitle,Long userId);
    @Query("select count(a) > 0 from Activity  a where a.title  =:activityTitle and a.user.id =:userId")
    boolean isActivityBelongToUserByTitle(Long userId, String activityTitle);
    @Query("select a from  Activity a where a.user.id =:userId")
    List<Activity> findAllByUserId(Long userId);

    Optional<Activity> findById(Long activityId);
    @Query("select count(b) > 0 from ActivityBanner b where b.activity.id =:activityId")
    boolean doesActivityHaveBanner(Long activityId);
}

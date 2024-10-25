package com.yevhenii.organisationSystem.repository;

import com.yevhenii.organisationSystem.entity.ActivityBanner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BanerRepository extends JpaRepository<ActivityBanner, Long> {

    Optional<ActivityBanner> findById(Long bannerId);

    @Query("select count(b) > 0 from ActivityBanner b  where b.id  =:bannerId and b.activity.user.id =:userId")
    boolean isBannerBelongToUser(Long userId, Long bannerId);

    @Query("select b from ActivityBanner  b where b.activity.id =:activityId")
    Optional<ActivityBanner> findByActivityId(Long activityId);
}

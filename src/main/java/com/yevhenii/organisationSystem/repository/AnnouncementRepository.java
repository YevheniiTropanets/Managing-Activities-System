package com.yevhenii.organisationSystem.repository;

import com.yevhenii.organisationSystem.entity.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement,Long> {

    List<Announcement> getAnnouncementByActivityBannerId(Long bannerId);

}

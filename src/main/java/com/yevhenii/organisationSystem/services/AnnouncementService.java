package com.yevhenii.organisationSystem.services;

import com.yevhenii.organisationSystem.entity.Announcement;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface AnnouncementService {


    void save(Announcement announcement);
    void delete(Announcement announcement);
    void update();
    List<Announcement> getAnnouncementByActivityBannerId(Long bannerId);

    Optional<Announcement> findById(Long announcementId);
}

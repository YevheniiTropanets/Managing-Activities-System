package com.yevhenii.organisationSystem.services.serviceImpl;

import com.yevhenii.organisationSystem.entity.Announcement;
import com.yevhenii.organisationSystem.repository.AnnouncementRepository;
import com.yevhenii.organisationSystem.services.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {

    AnnouncementRepository announcementRepository;
    @Autowired
    public AnnouncementServiceImpl(AnnouncementRepository announcementRepository) {
        this.announcementRepository = announcementRepository;
    }

    @Override
    public void save(Announcement announcement) {
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        announcement.setDatePosted(timestamp);
        announcementRepository.save(announcement);
    }

    @Override
    public void delete(Announcement announcement) {
        announcementRepository.delete(announcement);
    }

    @Override
    public void update() {

    }

    @Override
    public List<Announcement> getAnnouncementByActivityBannerId(Long bannerId) {
        return announcementRepository.getAnnouncementByActivityBannerId(bannerId);
    }

    @Override
    public Optional<Announcement> findById(Long announcementId) {
        return announcementRepository.findById(announcementId);
    }
}

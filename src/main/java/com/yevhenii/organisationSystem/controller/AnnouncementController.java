package com.yevhenii.organisationSystem.controller;

import com.yevhenii.organisationSystem.entity.ActivityBanner;
import com.yevhenii.organisationSystem.entity.Announcement;
import com.yevhenii.organisationSystem.services.AnnouncementService;
import com.yevhenii.organisationSystem.services.BanerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;

@Controller
public class AnnouncementController {
    AnnouncementService announcementService;
    BanerService banerService;
    @Autowired
    public AnnouncementController(BanerService banerService,AnnouncementService announcementService) {
        this.announcementService = announcementService;
        this.banerService = banerService;
    }
    @PostMapping("/announcement/create")
    public RedirectView createAnnouncement(@RequestParam String description,@RequestParam Long banerId){
        Announcement announcement = new Announcement();
        Optional<ActivityBanner> activityBanner = banerService.findById(banerId);
        announcement.setActivityBanner(activityBanner.get());
        announcement.setDescription(description);
        announcementService.save(announcement);
        return new RedirectView("/banner/details/" + banerId);
    }

    @GetMapping("/announcement/delete/{announcementId}")
    public RedirectView createAnnouncement(@PathVariable Long announcementId){
        Optional <Announcement> announcement= announcementService.findById(announcementId);
        announcementService.delete(announcement.get());
        return new RedirectView("/banner/details/" + announcement.get().getActivityBanner().getId());
    }



}

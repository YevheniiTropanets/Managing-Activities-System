package com.yevhenii.organisationSystem.controller;


import com.yevhenii.organisationSystem.controller.util.SecurityUtils;
import com.yevhenii.organisationSystem.dto.SaveBannerDTO;
import com.yevhenii.organisationSystem.entity.Activity;
import com.yevhenii.organisationSystem.entity.ActivityBanner;
import com.yevhenii.organisationSystem.entity.Announcement;
import com.yevhenii.organisationSystem.entity.PlannedActivities;
import com.yevhenii.organisationSystem.services.ActivityService;
import com.yevhenii.organisationSystem.services.AnnouncementService;
import com.yevhenii.organisationSystem.services.BanerService;
import com.yevhenii.organisationSystem.services.PlannedActivitiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
public class BannerController {
    BanerService banerService;
    PlannedActivitiesService plannedActivitiesService;
    ActivityService activityService;
    AnnouncementService announcementService;
    SecurityUtils securityUtils;

    @Autowired
    public BannerController(SecurityUtils securityUtils,AnnouncementService announcementService,ActivityService activityService,BanerService banerService,PlannedActivitiesService plannedActivitiesService) {
        this.plannedActivitiesService = plannedActivitiesService;
        this.banerService = banerService;
        this.activityService = activityService;
        this.announcementService = announcementService;
        this.securityUtils = securityUtils;
    }
    @Transactional
    @GetMapping("/banner/create/{activityId}")
    public String createBanner(@PathVariable Long activityId, Model model){
        Optional<Activity> activity = activityService.findById(activityId);
        model.addAttribute("activity",activity.get());
        return "createBannerPage";
    }



    @GetMapping("/banner/delete/{bannerId}")
    public RedirectView deleteBanner(@PathVariable Long bannerId){
        banerService.delete(bannerId);
        return new RedirectView("/activities");
    }

    @GetMapping("/banner/update/{banerId}")
    public String updateBanner(@PathVariable Long banerId, Model model){
        //Optional<Activity> activity = activityService.findById(activityId);
        Optional<ActivityBanner> activityBanner = banerService.findById(banerId);
        model.addAttribute("baner",activityBanner.get());
        //model.addAttribute("activity2",activity.get());
        return "updateBannerPage";
    }


    @PostMapping("banner/update")
    public RedirectView updateBanner(@RequestParam Long activityId,@RequestParam String description, @RequestPart("file")MultipartFile file) throws IOException {
        SaveBannerDTO saveBannerDTO = new SaveBannerDTO(description, file.getBytes(),activityId);
        System.out.println(activityId);
        banerService.update(saveBannerDTO,activityId);
        return  new RedirectView("/activities");
    }

    @PostMapping("banner/create")
    public RedirectView createBanner(@RequestParam Long activityId,@RequestParam String description, @RequestPart("file")MultipartFile file,Model model) throws IOException {
        SaveBannerDTO saveBannerDTO = new SaveBannerDTO(description, file.getBytes(),activityId);
        System.out.println(activityId);
        banerService.save(saveBannerDTO);
        return  new RedirectView("/activities");
    }
    @GetMapping("/banner/details/{banerId}")
    public String getBannerDetails(@PathVariable Long banerId,Model model){
        Optional<ActivityBanner> activityBanner = banerService.findById(banerId);
        model.addAttribute("baner",activityBanner.get());
        List<PlannedActivities> plannedActivitiesList = plannedActivitiesService.getPlannedActivities(activityBanner.get().getActivity().getId());
        model.addAttribute("plannedActivities",plannedActivitiesList);
        List<Announcement> announcementList = announcementService.getAnnouncementByActivityBannerId(banerId);
        model.addAttribute("announcements",announcementList);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.getPrincipal() instanceof org.springframework.security.core.userdetails.UserDetails){
            Activity activity = activityBanner.get().getActivity();
            boolean isActivityBelongTouser = activityService.isActivityBelongToUser(securityUtils.getUserId(), activity.getId());
            model.addAttribute("isActivityBelongToUser",isActivityBelongTouser);
        }
        return "bannerDetails";
    }

}

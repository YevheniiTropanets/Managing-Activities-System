package com.yevhenii.organisationSystem.controller;

import com.yevhenii.organisationSystem.controller.util.SecurityUtils;
import com.yevhenii.organisationSystem.dto.*;
import com.yevhenii.organisationSystem.dto.mapper.ApplicationMapper;
import com.yevhenii.organisationSystem.entity.Activity;
import com.yevhenii.organisationSystem.services.ActivityService;
import com.yevhenii.organisationSystem.services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;


@Controller
public class ActivityController {

    ActivityService activityService;
    SecurityUtils securityUtils;
    ProfileService profileService;
    ApplicationMapper applicationMapper;

    @Autowired
    public ActivityController(ApplicationMapper applicationMapper,ActivityService activityService, SecurityUtils securityUtils, ProfileService profileService) {
        this.securityUtils = securityUtils;
        this.activityService = activityService;
        this.profileService = profileService;
    }
    @GetMapping("/activity/{activityId}")
    public String getActivityDetails(@PathVariable Long activityId, Model model){
        Activity activity = activityService.findById(activityId)
                .orElseThrow(()-> new EntityNotFoundException("Activity with id" + activityId +" does not exist" ));

        model.addAttribute("activity",activity);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.getPrincipal() instanceof org.springframework.security.core.userdetails.UserDetails){
            boolean isActivityBelongTouser = activityService.isActivityBelongToUser(securityUtils.getUserId(),activityId);
            model.addAttribute("isActivityBelongToUser",isActivityBelongTouser);
            boolean doesActivityHaveBanner = activityService.doesActivityHaveBanner(activityId);
            model.addAttribute("doesActivityHaveBanner",doesActivityHaveBanner);
        }
        return "activityDetails";
    }



    @PostMapping("/activity/create")
    public RedirectView createActivity(
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam int amountSeats,
            @RequestParam String organisation,
            @RequestParam String genre,
            @RequestParam String activityType
    ) {
        SaveActivityDTO saveActivityDTO = new SaveActivityDTO(title, description, amountSeats, organisation, genre, activityType);
        activityService.save(saveActivityDTO);
        return new RedirectView("/activities");
    }

    @GetMapping("/activity/create")
    public String getCreateActivityForm(Model model) {
        Optional<ProfileDTO> profileDTO = profileService.findByUserId(securityUtils.getUserId());
        if (profileDTO.isPresent()) {
            ProfileDTO profile = profileDTO.get();
            model.addAttribute("profile", profile);
        } else {
            ProfileDTO profile2 = new ProfileDTO("", "", "", "");
            model.addAttribute("profile", profile2);
        }
        return "createActivity";
    }

    @GetMapping("/activities")
    public String getUserActivities(Model model) {
        model.addAttribute("activityList", activityService.findAllByUserId(securityUtils.getUserId()));
        return "userActivities";
    }

    @GetMapping({"/activity/getUpdateForm/{activityId}"})
    public String getUpdateVenuePage(@PathVariable Long activityId, Model model) {
        Activity activity = activityService.findById(activityId)
                .orElseThrow(() -> new EntityNotFoundException("User does not have  activities"));
        model.addAttribute("activity", activity);
        return "activityUpdate";
    }

    @PostMapping({"/activity/update/{activityId}"})
    public RedirectView updateVenue(
            @PathVariable Long activityId,
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam int amountSeats,
            @RequestParam String organisation,
            @RequestParam String genre,
            @RequestParam String activityType) {
        SaveActivityDTO saveActivityDTO = new SaveActivityDTO(title, description, amountSeats, organisation, genre, activityType);
        activityService.update(saveActivityDTO,activityId);
        return new RedirectView("/activities");
    }

    @GetMapping("/activity/delete/{activityId}")
    public RedirectView deleteActivity(@PathVariable Long activityId) {
        if (activityService.isActivityBelongToUser(securityUtils.getUserId(), activityId)) {
            activityService.delete(activityId);
            return new RedirectView("/activities");
        } else {
            throw new AccessDeniedException("Activity does not exist or user dont have access on it");
        }
    }
}

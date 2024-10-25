package com.yevhenii.organisationSystem.controller;

import com.yevhenii.organisationSystem.controller.util.SecurityUtils;
import com.yevhenii.organisationSystem.dto.ActivityDTO;
import com.yevhenii.organisationSystem.dto.ProfileDTO;
import com.yevhenii.organisationSystem.dto.VenueDTO;
import com.yevhenii.organisationSystem.services.ActivityService;
import com.yevhenii.organisationSystem.services.ProfileService;
import com.yevhenii.organisationSystem.services.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Optional;

@Controller
public class ProfileController {

    ProfileService profileService;
    SecurityUtils securityUtils;
    VenueService venueService;
    ActivityService activityService;

    @Autowired
    public ProfileController(ProfileService profileService, SecurityUtils securityUtils,VenueService venueService,ActivityService activityService) {
        this.profileService = profileService;
        this.securityUtils = securityUtils;
        this.venueService = venueService;
        this.activityService = activityService;
    }


    @GetMapping({"/profile/me"})
    public String getMyProfile(Model model) {
        List<VenueDTO> venueList = venueService.findAllById(securityUtils.getUserId());
        List<ActivityDTO> activityList = activityService.findAllByUserId(securityUtils.getUserId());
        model.addAttribute("venueAmount",venueList.size());
        model.addAttribute("activityAmount",activityList.size());
        Optional<ProfileDTO> profile = profileService.findByUserId(securityUtils.getUserId());
        profile.ifPresent(profileDTO -> model.addAttribute("profile", profileDTO));
        if(profile.isEmpty()){
            ProfileDTO profileDTO = new ProfileDTO("","","","");
            model.addAttribute("profile",profileDTO);
        }
        return "myProfile";
    }

    @GetMapping({"/profile/update"})
    public String getUpdateProfilePage(Model model) {
        Optional<ProfileDTO> profile = profileService.findByUserId(securityUtils.getUserId());
        profile.ifPresent(profileDTO -> model.addAttribute("profile", profileDTO));
        if(profile.isEmpty()){
            ProfileDTO profileDTO = new ProfileDTO("","","","");
            model.addAttribute("profile",profileDTO);
        }
        return "profileUpdate";
    }

    @PostMapping({"/profile/update"})
    public RedirectView updateProfile(@RequestParam String organisation,
                                      @RequestParam String firstname,
                                      @RequestParam String lastname,
                                      @RequestParam String phone
                                      ) {
        ProfileDTO profileDTO = new ProfileDTO(organisation,firstname,lastname,phone);
        profileService.update(profileDTO);
        return new RedirectView("/profile/me");
    }
}

package com.yevhenii.organisationSystem.controller;

import com.yevhenii.organisationSystem.controller.util.SecurityUtils;
import com.yevhenii.organisationSystem.dto.SaveVenueDTO;
import com.yevhenii.organisationSystem.dto.VenueDTO;
import com.yevhenii.organisationSystem.entity.City;
import com.yevhenii.organisationSystem.entity.PlannedActivities;
import com.yevhenii.organisationSystem.entity.Street;
import com.yevhenii.organisationSystem.services.CityService;
import com.yevhenii.organisationSystem.services.PlannedActivitiesService;
import com.yevhenii.organisationSystem.services.StreetService;
import com.yevhenii.organisationSystem.services.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import javax.persistence.EntityNotFoundException;
import java.util.List;


@Controller
public class VenueController {

    SecurityUtils securityUtils;
    VenueService venueService;
    CityService cityService;
    StreetService streetService;
    PlannedActivitiesService plannedActivitiesService;

    @Autowired
    public VenueController(PlannedActivitiesService plannedActivitiesService,VenueService venueService, SecurityUtils securityUtils, CityService cityService, StreetService streetService) {
        this.venueService = venueService;
        this.securityUtils = securityUtils;
        this.cityService = cityService;
        this.streetService = streetService;
        this.plannedActivitiesService = plannedActivitiesService;
    }

    @PostMapping("/venues/createVenue")
    public RedirectView addVenue(
            @RequestParam String title,
            @RequestParam int maximumSeats,
            @RequestParam String description,
            @RequestParam String adresIndex,
            @RequestParam int rentPrice,
            @RequestParam String streetName,
            @RequestParam String cityName
    ) {
        SaveVenueDTO saveVenueDTO = new SaveVenueDTO(title, maximumSeats, description, adresIndex, rentPrice, streetName, cityName);
        venueService.save(saveVenueDTO);
        return new RedirectView("/venues");
    }

    @GetMapping("/venues/create")
    public String getCreateVenueForm(Model model) {
        List<City> cityList = cityService.findAll();
        model.addAttribute("cityList", cityList);
        List<Street> streetList = streetService.findAll();
        model.addAttribute("streetList", streetList);
        return "createVenue";
    }


    @GetMapping("/venues/{venueId}")
    public String getVenueInfo(@PathVariable Long venueId, Model model) {
        VenueDTO VenueDTO = venueService.findById(venueId).
                orElseThrow(() -> new EntityNotFoundException("Venue with id " + venueId + " does not exist"));
        model.addAttribute("venue", VenueDTO);
        List<PlannedActivities> plannedActivitiesList = plannedActivitiesService.getPlannedActivitiesByVenue(venueId);
        model.addAttribute("plannedActivities",plannedActivitiesList);
        return "venueDetails";
    }

    @GetMapping({"/venues/getUpdateForm/{venueId}"})
    public String getUpdateVenuePage(@PathVariable Long venueId, Model model) {
        VenueDTO venueDTO = venueService.findById(venueId)
                .orElseThrow(() -> new EntityNotFoundException("User does not have a venues"));
        model.addAttribute("venue", venueDTO);
        return "venueUpdate";
    }


    @GetMapping("/venues")
    public String getUserVenues(Model model) {
        long userId = securityUtils.getUserId();
        model.addAttribute("userVenuesList", venueService.findAllById(userId));
        return "userVenues";
    }

    @GetMapping("/venues/delete/{venueId}")
    public RedirectView deleteVenue(@PathVariable Long venueId) {
        venueService.delete(venueId);
        return new RedirectView("/venues");
    }

    @PostMapping({"/venues/update/{venueId}"})
    public RedirectView updateVenue(@PathVariable Long venueId,
                                    @RequestParam String title,
                                    @RequestParam int maximumSeats,
                                    @RequestParam String description,
                                    @RequestParam String adresIndex,
                                    @RequestParam int rentPrice,
                                    @RequestParam String streetName,
                                    @RequestParam String cityName) {
        SaveVenueDTO venueDTO = new SaveVenueDTO(title, maximumSeats, description, adresIndex, rentPrice, streetName, cityName);
        venueService.update(venueDTO, venueId);
        return new RedirectView("/venues");
    }


}

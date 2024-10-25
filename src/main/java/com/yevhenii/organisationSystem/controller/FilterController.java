package com.yevhenii.organisationSystem.controller;

import com.yevhenii.organisationSystem.controller.util.SecurityUtils;
import com.yevhenii.organisationSystem.dto.mapper.ApplicationMapper;
import com.yevhenii.organisationSystem.entity.Edge;
import com.yevhenii.organisationSystem.entity.PlannedActivities;
import com.yevhenii.organisationSystem.entity.Venue;
import com.yevhenii.organisationSystem.repository.EdgeRepository;
import com.yevhenii.organisationSystem.services.PlannedActivitiesService;
import com.yevhenii.organisationSystem.services.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Controller
public class FilterController {

    VenueService venueService;
    ApplicationMapper applicationMapper;
    EdgeRepository edgeService;
    SecurityUtils securityUtils;
    PlannedActivitiesService plannedActivitiesService;

    @Autowired
    public FilterController(PlannedActivitiesService plannedActivitiesService, VenueService venueService, ApplicationMapper applicationMapper, EdgeRepository edgeService, SecurityUtils securityUtils) {
        this.venueService = venueService;
        this.applicationMapper = applicationMapper;
        this.edgeService = edgeService;
        this.securityUtils = securityUtils;
        this.plannedActivitiesService = plannedActivitiesService;
    }

    @PostMapping("/filter")
    public RedirectView filterVenues(
            @RequestParam(required = true) String filterDate,
            @RequestParam(required = false) String filterCity,
            @RequestParam(required = false) Double filterPrice,
            @RequestParam(required = false) Integer filterCapacity,
            RedirectAttributes redirectAttributes) {
        List<Venue> listForFilter;
        System.out.println(filterDate);
        if (filterDate != null) {
            LocalDate date = LocalDate.parse(filterDate);
            System.out.println(date);
            listForFilter = venueService.findAllFreeVenuesForDate(date);
        } else {
            listForFilter = venueService.findAllFreeVenuesForCurrentDate();
        }
        System.out.println(listForFilter);
        List<Venue> filteredList = listForFilter.stream()
                .filter(venue -> (filterCity == null || venue.getStreet().getCity().getCityName().equalsIgnoreCase(filterCity)))
                .filter(venue -> (filterPrice == null || venue.getRentPrice() >= filterPrice))
                .filter(venue -> (filterCapacity == null || venue.getMaximumSeats() >= filterCapacity))
                .collect(Collectors.toList());

        redirectAttributes.addFlashAttribute("filteredList", filteredList);
        return new RedirectView("/sendApplicationForm");
    }

    @PostMapping("/ownerFilter")
    public RedirectView filterEdgesForVenues(
            @RequestParam(required = true) String filterDate,
            @RequestParam(required = false) String filterVenue,
            RedirectAttributes redirectAttributes) {
        List<Edge> listForFilter;
        System.out.println(filterDate);

        //Timestamp date = Timestamp.valueOf(filterDate +" " + "00:00:00");
        LocalDate localDate = LocalDate.parse(filterDate);
        System.out.println(localDate);
        listForFilter = edgeService.findUserEdgesForDate(localDate, securityUtils.getUserId());
        System.out.println(listForFilter);


        List<Edge> filteredList;
        if (!filterVenue.equals("")) {
            filteredList = listForFilter.stream()
                    .filter(edge -> (filterVenue == null || edge.getVenue().getTitle().equalsIgnoreCase(filterVenue)))
                    .collect(Collectors.toList());
        } else filteredList = listForFilter;
        redirectAttributes.addFlashAttribute("filteredOwnerList", filteredList);
        return new RedirectView("/getOwnerApplications/1");
    }

    @PostMapping("/organisatorFilter")
    public RedirectView filterEdgesForApplication(
            @RequestParam(required = true) String filterDate,
            @RequestParam String filterActivity,
            RedirectAttributes redirectAttributes) {
        List<Edge> listForFilter;
        System.out.println(filterDate);
        LocalDate localDate = LocalDate.parse(filterDate);
        System.out.println(localDate);
        listForFilter = edgeService.findEdgesByOrganisatorAndDate(localDate, securityUtils.getUserId());
        System.out.println(listForFilter);
        List<Edge> filteredList = listForFilter.stream()
                .filter(edge -> (filterActivity == null || edge.getApplicationToGetVenue().getActivity().getTitle().equalsIgnoreCase(filterActivity)))
                .collect(Collectors.toList());
        System.out.println(filteredList);
        redirectAttributes.addFlashAttribute("filteredOrganisatorList", filteredList);
        return new RedirectView("/getOrganisatorApplication/1");
    }

    @PostMapping("/filterPlanned")
    public RedirectView filterPlannedActivities(
            @RequestParam(required = true) String filterDate,
            @RequestParam String filterGenre,
            @RequestParam (required = false) String filterStyle,
            RedirectAttributes redirectAttributes) {
        List<PlannedActivities> listForFilter;
        LocalDate localDate = LocalDate.parse(filterDate);
        listForFilter = plannedActivitiesService.findAllForToday();

        List<PlannedActivities> filteredList = listForFilter.stream()
                .filter(plannedActivity -> (localDate == null || plannedActivity.getStartDate().toLocalDateTime().toLocalDate().isEqual(localDate)) &&
                                (filterGenre == null || filterGenre.isEmpty() || plannedActivity.getActivity().getGenre().equalsIgnoreCase(filterGenre)) &&
                                (filterStyle == null || filterStyle.isEmpty() || plannedActivity.getActivity().getActivityType().equalsIgnoreCase(filterStyle))
                )
                .collect(Collectors.toList());
        System.out.println(filteredList);
        redirectAttributes.addFlashAttribute("filteredOrganisatorList", filteredList);
        return new RedirectView("/index");
    }


}

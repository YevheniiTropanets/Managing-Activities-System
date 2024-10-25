package com.yevhenii.organisationSystem.controller;

import com.yevhenii.organisationSystem.controller.util.SecurityUtils;
import com.yevhenii.organisationSystem.dto.ActivityDTO;
import com.yevhenii.organisationSystem.dto.SaveApplicationDTO;
import com.yevhenii.organisationSystem.entity.City;
import com.yevhenii.organisationSystem.entity.Edge;
import com.yevhenii.organisationSystem.entity.Venue;
import com.yevhenii.organisationSystem.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
public class ApplicationController {

    ApplicationService applicationService;
    ActivityService activityService;
    SecurityUtils securityUtils;
    VenueService venueService;
    CityService cityService;
    EdgeService edgeService;

    @Autowired
    public ApplicationController(EdgeService edgeService, CityService cityService, VenueService venueService, SecurityUtils securityUtils, ApplicationService applicationService, ActivityService activityService) {
        this.applicationService = applicationService;
        this.activityService = activityService;
        this.securityUtils = securityUtils;
        this.venueService = venueService;
        this.cityService = cityService;
        this.edgeService = edgeService;
    }

    private HttpSession getSession() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attr.getRequest().getSession(true);
    }

    @GetMapping("/getAlgoResultPage")
    public String getResultPage(Model model) {
        HttpSession session = getSession();
        Object resultAlgorithm = session.getAttribute("resultAlgorithm");
        if (resultAlgorithm != null) {
            model.addAttribute("resultList", resultAlgorithm);
        } else {
            System.out.println("NO FILTER");
        }

        return "getAlgoResultPage";
    }

    @GetMapping("/sendApplicationForm")
    public String getCreateApplicationForm(Model model) {

        List<ActivityDTO> activityDTOList = activityService.findAllByUserId(securityUtils.getUserId());
        model.addAttribute("activityList", activityDTOList);

        if (model.containsAttribute("filteredList")) {
            model.addAttribute("venueList", model.getAttribute("filteredList"));
        } else {
            List<Venue> freeVenueList = venueService.findAllFreeVenuesForCurrentDate();
            model.addAttribute("venueList", freeVenueList);
        }
        List<City> cityList = cityService.findAll();
        model.addAttribute("cityList", cityList);
        return "sendApplicationForm";
    }

    //    @GetMapping("/getOwnerApplications")
//    public String getOwnerApplications(Model model) {
//        //Page<Edge> edgesPage = applicationService.findPaginated(securityUtils.getUserId(), PageRequest.of(page - 1, size));
//        model.addAttribute("venueList", venueService.findAllById(securityUtils.getUserId()));
//        if (model.containsAttribute("filteredOwnerList")) {
//            model.addAttribute("ownerEdges", model.getAttribute("filteredOwnerList"));
//        } else {
//            System.out.println("NO FILTER");
//            model.addAttribute("ownerEdges", applicationService.findAllForOwner(securityUtils.getUserId()));
//        }
//        return "ownerApplications";
//    }
    @GetMapping("/getOwnerApplications/{currentPage}")
    public String getOwnerApplications(Model model, @PathVariable("currentPage") int page) {
        Page<Edge> edgePage = applicationService.findAllForOwnerPaginated(securityUtils.getUserId(), page, 10);
        long totalApplications = edgePage.getTotalElements();
        int totalPages = edgePage.getTotalPages();
        List<Edge> edgeList = edgePage.getContent();
        if (model.containsAttribute("filteredOwnerList")) {
            model.addAttribute("ownerEdges", model.getAttribute("filteredOwnerList"));
        } else {
            model.addAttribute("ownerEdges", edgeList);
        }
        model.addAttribute("totalApplications",totalApplications);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);
        return "ownerApplications";
    }

//    @GetMapping("/getOrganisatorApplication")
//    public String getOrganisatorApplications(Model model) {
//        //Page<Edge> edgesPage = applicationService.findPaginated(securityUtils.getUserId(), PageRequest.of(page - 1, size));
//        model.addAttribute("activityList", activityService.findAllByUserId(securityUtils.getUserId()));
//        model.addAttribute("orgApplications", edgeService.findOrganisatorApplicationsByUserId(securityUtils.getUserId()));
//        if (model.containsAttribute("filteredOrganisatorList")) {
//            model.addAttribute("orgApplications", model.getAttribute("filteredOrganisatorList"));
//        } else {
//            model.addAttribute("orgApplications", edgeService.findOrganisatorApplicationsByUserId(securityUtils.getUserId()));
//        }
//        return "organisatorApplications";
//    }

    @GetMapping("/getOrganisatorApplication/{pageNumber}")
    public String getOrganisatorApplications(Model model, @PathVariable("pageNumber") int currentPage) {
        Page<Edge> page = edgeService.findOrganisatorApplicationsByUserIdPaginated(securityUtils.getUserId(), currentPage, 10);
        int totalPages = page.getTotalPages();
        long totalItems = page.getTotalElements();
        List<Edge> edges = page.getContent();
        model.addAttribute("activityList", activityService.findAllByUserId(securityUtils.getUserId()));
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("orgApplications", edges);

        if (model.containsAttribute("filteredOrganisatorList")) {
            model.addAttribute("orgApplications", model.getAttribute("filteredOrganisatorList"));
        } else {
            model.addAttribute("orgApplications", edges);
        }

        return "organisatorApplications";
    }


    @PostMapping("/application/create")
    public RedirectView createApplication(@RequestBody SaveApplicationDTO saveApplication) {

        if (activityService.isActivityBelongToUserByTitle(securityUtils.getUserId(), saveApplication.getActivityTitle())) {
            applicationService.save(saveApplication);
            return new RedirectView("/application/my");
        } else {
            throw new AccessDeniedException("Venue does not exist or user dont have access on it");
        }
    }


    @PostMapping("/sendApplication")
    public RedirectView sendApplication(@RequestParam String startDate,
                                        @RequestParam String startTimeHour,
                                        @RequestParam String startTimeMin,
                                        @RequestParam String endDate,
                                        @RequestParam String endTimeHour,
                                        @RequestParam String endTimeMin,
                                        @RequestParam String activityTitle,
                                        @RequestParam List<Venue> venueList
    ) {
        String startTime = startTimeHour + ":" + startTimeMin;
        String endTime = endTimeHour + ":" + endTimeMin;
        SaveApplicationDTO saveApplication = new SaveApplicationDTO(startDate, startTime, endDate, endTime, activityTitle);
        if (activityService.isActivityBelongToUserByTitle(securityUtils.getUserId(), saveApplication.getActivityTitle())) {
            applicationService.sendApplication(saveApplication, venueList);
            return new RedirectView("/index");
        } else {
            throw new AccessDeniedException("Application does not exist or user dont have access on it");
        }
    }

    @GetMapping({"/venueHolderApplications/decline/{id}"})
    public RedirectView decline(@PathVariable Long id) {
        applicationService.decline(id);
        return new RedirectView("/getOwnerApplications");
    }

    @GetMapping({"/venueHolderApplications/approve/{id}"})
    public RedirectView approve(@PathVariable Long id) {
        applicationService.approve(id);

        return new RedirectView("/getOwnerApplications");
    }


    @GetMapping("/applications/delete/{edgeId}")
    public RedirectView deleteApplication(@PathVariable Long edgeId) {
        applicationService.delete(edgeId);
        return new RedirectView("/getOrganisatorApplication");
    }


}

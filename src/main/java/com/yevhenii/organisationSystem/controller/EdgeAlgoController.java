package com.yevhenii.organisationSystem.controller;

import com.yevhenii.organisationSystem.controller.util.SecurityUtils;
import com.yevhenii.organisationSystem.entity.ApplicationToGetVenue;
import com.yevhenii.organisationSystem.entity.Edge;
import com.yevhenii.organisationSystem.entity.Venue;
import com.yevhenii.organisationSystem.entity.enums.EApplicationStatus;
import com.yevhenii.organisationSystem.services.ApplicationService;
import com.yevhenii.organisationSystem.services.EdgeService;
import com.yevhenii.organisationSystem.services.KunAlgorithmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Controller
public class EdgeAlgoController {

    KunAlgorithmService kunAlgorithmService;
    EdgeService edgeService;
    SecurityUtils securityUtils;
    ApplicationService applicationService;
    @Autowired
    public EdgeAlgoController(ApplicationService applicationService,SecurityUtils securityUtils,KunAlgorithmService kunAlgorithmService, EdgeService edgeService) {
        this.edgeService = edgeService;
        this.kunAlgorithmService = kunAlgorithmService;
        this.securityUtils = securityUtils;
        this.applicationService = applicationService;
    }

    private HttpSession getSession() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attr.getRequest().getSession(true);
    }

    @PostMapping("/manageVenues")
    public RedirectView manageVenues(@RequestParam String date,
                                     RedirectAttributes redirectAttributes,
                                     Model model) {
        LocalDate localDate = LocalDate.parse(date);
        List<Edge> edgeList = kunAlgorithmService.findUserEdgesForDateAndStatus(localDate, securityUtils.getUserId(), EApplicationStatus.EXPECT);
        Map<Venue, ApplicationToGetVenue> result = kunAlgorithmService.doKuhnAlgorithm(edgeList);
        List<Edge> resultList = kunAlgorithmService.getKuhnResultList(edgeList);
        //redirectAttributes.addFlashAttribute("resultAlgorithm",resultList);
        HttpSession session = getSession();
        session.setAttribute("resultAlgorithm", resultList);
        return new RedirectView ("/getAlgoResultPage");
    }

    @GetMapping({"/algoApplications/decline/{id}"})
    public RedirectView decline(@PathVariable Long id) {
        applicationService.decline(id);
        return new RedirectView("/getAlgoResultPage");
    }

    @GetMapping({"/algoApplications/approve/{id}"})
    public RedirectView approve(@PathVariable Long id) {
        applicationService.approve(id);
        return new RedirectView("/getAlgoResultPage");
    }




    @PostMapping("/get")
    public Edge getEdge(@RequestParam Long id){
        Optional<Edge> edgeOptional = edgeService.findById(id);
        return edgeOptional.get();
    }

}

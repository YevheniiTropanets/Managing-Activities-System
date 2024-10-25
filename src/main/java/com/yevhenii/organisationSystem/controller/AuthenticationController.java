package com.yevhenii.organisationSystem.controller;

import com.yevhenii.organisationSystem.controller.util.SecurityUtils;
import com.yevhenii.organisationSystem.controller.util.SessionUtils;
import com.yevhenii.organisationSystem.entity.PlannedActivities;
import com.yevhenii.organisationSystem.security.pojo.SignUpRequest;
import com.yevhenii.organisationSystem.services.PlannedActivitiesService;
import com.yevhenii.organisationSystem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
public class AuthenticationController {

    SecurityUtils securityUtils;
    SessionUtils sessionUtils;
    UserService userService;
    PasswordEncoder passwordEncoder;
    AuthenticationManager authenticationManager;
    PlannedActivitiesService plannedActivitiesService;

    @Autowired
    public AuthenticationController(SecurityUtils securityUtils,
                                    SessionUtils sessionUtils,
                                    UserService userService,
                                    PasswordEncoder passwordEncoder,
                                    AuthenticationManager authenticationManager,
                                    PlannedActivitiesService plannedActivitiesService) {
        this.securityUtils = securityUtils;
        this.sessionUtils = sessionUtils;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.plannedActivitiesService = plannedActivitiesService;
    }

    @GetMapping({"/index", "/"})
    public String index(Model model) {

        if (model.containsAttribute("filteredOrganisatorList")) {
            model.addAttribute("plannedActivities", model.getAttribute("filteredOrganisatorList"));
        } else {
            List<PlannedActivities> plannedActivitiesList = plannedActivitiesService.findAllForToday();
            model.addAttribute("plannedActivities", plannedActivitiesList);
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.getPrincipal() instanceof org.springframework.security.core.userdetails.UserDetails) model.addAttribute("isAdmin",securityUtils.checkAdmin());
        return "index";
    }

    @GetMapping({"/register"})
    public String register() {
        return "register";
    }


    @GetMapping("/login")
    public String login() {

        return "login";
    }

//

    @PostMapping("/login")
    public RedirectView login(@RequestParam String email,
                              @RequestParam String password,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        if (model.containsAttribute("errorMessage")) {
            model.addAttribute("errorMessage", model.getAttribute("errorMessage"));
        }
        if (email.equals("") || password.equals("")) {
            redirectAttributes.addFlashAttribute("errorMessage", "Введено пусті поля");
            return new RedirectView("/login");
        }


        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(email, password));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return new RedirectView("/index");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Неправильний логін або пароль");
            return new RedirectView("/login");
        }
    }

    @PostMapping("/register")
    public RedirectView register(@RequestParam String email,
                                 @RequestParam String password,
                                 Model model,
                                 RedirectAttributes redirectAttributes) {
        if (model.containsAttribute("errorMessage")) {
            model.addAttribute("errorMessage", model.getAttribute("errorMessage"));
        }
        if (email.equals("") || password.equals("")) {
            redirectAttributes.addFlashAttribute("errorMessage", "Введено пусті поля");
            return new RedirectView("/register");
        } else if (userService.findByEmail(email).isPresent()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ця електронна пошта вже використовується");
            return new RedirectView("/register");
        }

        if (userService.findByEmail(email).isEmpty()) {
            userService.register(new SignUpRequest(
                    email,
                    passwordEncoder.encode(password)
            ));
        }
        return new RedirectView("login");
    }


}

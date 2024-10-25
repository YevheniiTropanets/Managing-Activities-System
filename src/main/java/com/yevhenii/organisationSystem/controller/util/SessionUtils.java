package com.yevhenii.organisationSystem.controller.util;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Component
public class SessionUtils {
    private static final String PROFILE_ID = "PROFILE_ID";

    public Object getProfileId(HttpSession session) {
        return session.getAttribute(PROFILE_ID);
    }

    public void setProfileId(HttpSession session, long value) {
        session.setAttribute(PROFILE_ID, value);
    }

}

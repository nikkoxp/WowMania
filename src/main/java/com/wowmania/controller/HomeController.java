package com.wowmania.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Authentication auth, HttpSession session, Model model) {
        if (auth != null && auth.isAuthenticated()) {
            String username = auth.getName();
            model.addAttribute("username", username);

            Integer visits = (Integer) session.getAttribute("visits");
            if (visits == null) visits = 0;
            session.setAttribute("visits", visits + 1);
            model.addAttribute("visits", visits + 1);

            return "home-loggedin";
        } else {
            return "home";
        }
    }

    @GetMapping("/test-home-loggedin")
    public String testLoggedInView(Model m) {
        m.addAttribute("username","TestUser");
        m.addAttribute("visits", 1);
        return "home-loggedin";
    }

}

package com.wowmania.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Authentication auth, Model model) {
        if (auth != null && auth.isAuthenticated()) {
            model.addAttribute("username", auth.getName());
            return "home-loggedin";
        } else {
            return "home";
        }
    }
}

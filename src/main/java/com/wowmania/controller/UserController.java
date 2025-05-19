package com.wowmania.controller;

import com.wowmania.dto.UserDto;
import com.wowmania.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {
    @Autowired private UserService userService;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new UserDto());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") UserDto dto) {
        userService.registerUser(dto);
        return "redirect:/login";
    }

    @GetMapping("/custom-login")
    public String showLoginPage() {
        return "login";
    }
}

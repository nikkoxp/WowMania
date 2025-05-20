package com.wowmania.controller;

import com.wowmania.dto.UserDto;
import com.wowmania.model.User;
import com.wowmania.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProfileController {

    @Autowired private UserService userService;

    @GetMapping("/profile")
    public String privateProfile(Authentication auth, Model model) {
        User user = userService.findByUsername(auth.getName());
        UserDto dto = new UserDto();
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setBuyer(user.getRoles().stream().anyMatch(r->r.getName().equals("ROLE_BUYER")));
        dto.setSeller(user.getRoles().stream().anyMatch(r->r.getName().equals("ROLE_SELLER")));
        dto.setInGameName(user.getInGameName());
        dto.setAllegiance(user.getAllegiance());
        model.addAttribute("userDto", dto);
        return "profile";
    }

    @PostMapping("/profile")
    public String updateProfile(@ModelAttribute("userDto") UserDto dto,
                                Authentication auth, Model model) {
        try {
            userService.updateProfile(
                    auth.getName(),
                    dto.getEmail(),
                    dto.getPassword(),
                    dto.isBuyer(),
                    dto.isSeller(),
                    dto.getInGameName(),
                    dto.getAllegiance()
            );
            model.addAttribute("success", "Profile updated");
        } catch(Exception e) {
            model.addAttribute("error", e.getMessage());
        }

        User updatedUser = userService.findByUsername(auth.getName());
        UserDto updatedDto = new UserDto();
        updatedDto.setUsername(updatedUser.getUsername());
        updatedDto.setEmail(updatedUser.getEmail());
        updatedDto.setBuyer(updatedUser.getRoles()
                .stream().anyMatch(r -> r.getName().equals("ROLE_BUYER")));
        updatedDto.setSeller(updatedUser.getRoles()
                .stream().anyMatch(r -> r.getName().equals("ROLE_SELLER")));
        updatedDto.setInGameName(updatedUser.getInGameName());
        updatedDto.setAllegiance(updatedUser.getAllegiance());

        model.addAttribute("userDto", updatedDto);

        return "profile";
    }

    @GetMapping("/users/{username}")
    public String publicProfile(@PathVariable String username, Model model) {
        User user = userService.findByUsername(username);
        if (user==null) return "redirect:/";
        model.addAttribute("user", user);
        return "public_profile";
    }
}

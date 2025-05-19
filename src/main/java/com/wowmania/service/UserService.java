package com.wowmania.service;

import com.wowmania.dto.UserDto;
import com.wowmania.model.Role;
import com.wowmania.model.User;
import com.wowmania.repository.RoleRepository;
import com.wowmania.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {
    @Autowired private UserRepository userRepo;
    @Autowired private RoleRepository roleRepo;
    @Autowired private PasswordEncoder passwordEncoder;

    public User findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    public void registerUser(UserDto dto) {
        if (userRepo.existsByUsername(dto.getUsername())) {
            throw new IllegalArgumentException("Username already taken");
        }
        if (userRepo.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email already registered");
        }
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        Set<Role> roles = new HashSet<>();
        if (dto.isBuyer()) {
            Role r = roleRepo.findByName("ROLE_BUYER");
            if (r==null) { r = new Role(); r.setName("ROLE_BUYER"); roleRepo.save(r); }
            roles.add(r);
        }
        if (dto.isSeller()) {
            Role r = roleRepo.findByName("ROLE_SELLER");
            if (r==null) { r = new Role(); r.setName("ROLE_SELLER"); roleRepo.save(r); }
            roles.add(r);
        }
        user.setRoles(roles);
        userRepo.save(user);
    }

    public void updateProfile(String username, String newEmail, String newPassword, boolean buyer, boolean seller) {
        User user = userRepo.findByUsername(username);
        user.setEmail(newEmail);
        if (newPassword!=null && !newPassword.isBlank()) {
            user.setPassword(passwordEncoder.encode(newPassword));
        }
        Set<Role> roles = new HashSet<>();
        if (buyer) roles.add(roleRepo.findByName("ROLE_BUYER"));
        if (seller) roles.add(roleRepo.findByName("ROLE_SELLER"));
        user.setRoles(roles);
        userRepo.save(user);
    }
}

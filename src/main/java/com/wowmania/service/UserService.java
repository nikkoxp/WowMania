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

@Service
public class UserService {
    @Autowired private UserRepository userRepo;
    @Autowired private RoleRepository roleRepo;
    @Autowired private PasswordEncoder passwordEncoder;

    public void registerUser(UserDto dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        Role role = roleRepo.findByName("ROLE_USER");
        if (role == null) {
            role = new Role();
            role.setName("ROLE_USER");
            roleRepo.save(role);
        }
        user.setRoles(Collections.singleton(role));

        userRepo.save(user);
    }
}

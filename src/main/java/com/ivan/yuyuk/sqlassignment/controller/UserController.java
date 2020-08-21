package com.ivan.yuyuk.sqlassignment.controller;

import com.ivan.yuyuk.sqlassignment.entity.User;
import com.ivan.yuyuk.sqlassignment.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class UserController {

    private UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/registration")
    public String getRegistrationPage() {
        return "registrationPage";
    }

    @PostMapping("/registration")
    public String addUser(User user) {
        Optional<User> userFromDB = userRepository.findByUserName(user.getUserName());
        if (userFromDB.isPresent()) {
            return "redirect:/registration";
        }
        user.setActive(true);
        user.setRoles("USER");
        userRepository.save(user);
        return "redirect:/login";
    }
}

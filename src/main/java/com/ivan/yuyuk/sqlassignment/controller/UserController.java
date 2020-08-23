package com.ivan.yuyuk.sqlassignment.controller;

import com.ivan.yuyuk.sqlassignment.entity.User;
import com.ivan.yuyuk.sqlassignment.service.ApplicationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class UserController {

    private ApplicationService service;

    public UserController(ApplicationService service) {
        this.service = service;
    }

    @GetMapping("/registration")
    public String getRegistrationPage(Model model, @RequestParam(name = "registration_error", required = false) String isError) {
        if (isError != null) {
            model.addAttribute("errorMessage", "Пользователь с таким именем уже существует!");
        }
        return "registrationPage";
    }

    @PostMapping("/registration")
    public String addUser(User user) {
        boolean isSuccessRegistration = service.doRegistration(user);
        if (isSuccessRegistration) {
            return "redirect:/login";
        }
        return "redirect:/registration?registration_error=true";
    }
}

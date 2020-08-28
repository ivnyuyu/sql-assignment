package com.ivan.yuyuk.sqlassignment.controller;

import com.ivan.yuyuk.sqlassignment.dto.UserDTO;
import com.ivan.yuyuk.sqlassignment.entity.User;
import com.ivan.yuyuk.sqlassignment.service.ApplicationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
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
        if (!model.containsAttribute("userDTO")) {
            model.addAttribute("userDTO", new UserDTO());
        }

        return "registrationPage";
    }

    @PostMapping("/registration")
    public String addUser(@Valid UserDTO userDTO, BindingResult validationResult, RedirectAttributes redirectAttributes) {
        if(validationResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userDTO", validationResult);
            redirectAttributes.addFlashAttribute("userDTO",userDTO);
            return "redirect:/registration";
        }
        boolean isSuccessRegistration = false;
        try {
            isSuccessRegistration = service.doRegistration(userDTO);
        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
        }
        if (isSuccessRegistration) {
            return "redirect:/login";
        }
        return "redirect:/registration?registration_error=true";
    }
}

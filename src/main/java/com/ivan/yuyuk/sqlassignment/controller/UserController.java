package com.ivan.yuyuk.sqlassignment.controller;

import com.ivan.yuyuk.sqlassignment.entity.User;
import com.ivan.yuyuk.sqlassignment.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

/*    @GetMapping("/login")
    public String getLoginPage() {
        return "loginPage";
    }*/

    @GetMapping("/registration")
    public String getRegistrationPage(Model model) {
        model.addAttribute("userReg",new User());
        return "registrationPage";
    }

    @PostMapping("/registration")
    public String addUser(User user , Model model) {
        User userFromDB = userRepository.findUserByLogin(user.getLogin());
        if(userFromDB != null) {
            return "redirect:/registration";
        }
        userRepository.save(user);
        return "redirect:/login";
    }
}

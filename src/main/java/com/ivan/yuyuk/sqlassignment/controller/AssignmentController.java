package com.ivan.yuyuk.sqlassignment.controller;

import com.ivan.yuyuk.sqlassignment.entity.Assignment;
import com.ivan.yuyuk.sqlassignment.model.AnswerFromUserForm;
import com.ivan.yuyuk.sqlassignment.repository.AssignmentDAO;
import com.ivan.yuyuk.sqlassignment.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class AssignmentController {

    private AssignmentService assignmentService;

    @Autowired
    public AssignmentController(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    @GetMapping("/")
    public String assignmentPage(Model model) {
        List<Assignment> result = assignmentService.getAllAssignments();
        model.addAttribute("assignments", result);
        return "allAssignments";
    }

    @GetMapping("/assignment")
    public String assignment(@RequestParam("assignmentId") Long id, Model model, HttpServletResponse response, HttpServletRequest request) {
        Assignment assignment = assignmentService.getAssignmentById(id);
        model.addAttribute("assignment", assignment);
        AnswerFromUserForm userFormAnswer = new AnswerFromUserForm();
        model.addAttribute("answer", userFormAnswer);
        return "solveAssignmentPage";
    }

    @PostMapping("/check")
    public String checkAnswer(@ModelAttribute("answer") AnswerFromUserForm answer, HttpServletResponse response, HttpServletRequest request) {
        //request.getCookies();
        //response.addCookie(new Cookie("name", "Dmitriy"));
        assignmentService.checkUserQuery(answer.getId(), answer.getAnswer());
        return "redirect:/assignment?assignmentId=" + answer.getId();
    }
}

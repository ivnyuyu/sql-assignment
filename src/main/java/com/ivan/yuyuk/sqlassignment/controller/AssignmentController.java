package com.ivan.yuyuk.sqlassignment.controller;

import com.ivan.yuyuk.sqlassignment.entity.Assignment;
import com.ivan.yuyuk.sqlassignment.model.AnswerFromUserForm;
import com.ivan.yuyuk.sqlassignment.service.ApplicationService;
import com.ivan.yuyuk.sqlassignment.service.CookieManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class AssignmentController {

    private final ApplicationService service;
    private final CookieManagerService cookieManagerService;

    @Autowired
    public AssignmentController(ApplicationService service, CookieManagerService cookieManagerService) {
        this.cookieManagerService = cookieManagerService;
        this.service = service;
    }

    @GetMapping("/")
    public String assignmentPage(Model model) {
        model.addAttribute("assignments", service.getAllAssignments());
        return "allAssignments";
    }

    @GetMapping("/assignment/{assignmentId}")
    public String assignment(@PathVariable("assignmentId") Long id, Model model, HttpServletRequest request) {
        Assignment assignment = service.getAssignmentById(id);
        model.addAttribute("assignment", assignment);
        AnswerFromUserForm tryFromCookie = cookieManagerService.getUserQueryFromCookie(id, request.getCookies());
        model.addAttribute("answer", tryFromCookie);
        if (tryFromCookie.getAnswer() != null && Boolean.TRUE.equals(tryFromCookie.getStatus().isExecuteQuery())) {
            model.addAttribute("userQueryResult", service.getResult(tryFromCookie.getAnswer()));
        }
        model.addAttribute("expectedResult", service.getResult(assignment.getCorrectQuery()));
        return "solveAssignmentPage";
    }

    //возможно стоит использовать PathVariable.
    @PostMapping("/check")
    public String checkAnswer(@ModelAttribute("answer") AnswerFromUserForm answer, HttpServletResponse response) {
        answer.setStatus(service.saveUserCorrectAnswer(answer.getId(),answer.getAnswer()));
        cookieManagerService.addUserQueryToCookie(answer, response);
        return "redirect:/assignment/" + answer.getId();
    }

    @GetMapping("/userProgress")
    public String getAll(Model model) {
        model.addAttribute("progress", service.findAllCorrectAssignmentsByCurrentUser());
        return "userProgress";
    }
}

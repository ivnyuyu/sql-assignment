package com.ivan.yuyuk.sqlassignment.controller;

import com.ivan.yuyuk.sqlassignment.entity.Assignment;
import com.ivan.yuyuk.sqlassignment.entity.User;
import com.ivan.yuyuk.sqlassignment.model.AnswerFromUserForm;
import com.ivan.yuyuk.sqlassignment.service.AssignmentService;
import com.ivan.yuyuk.sqlassignment.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

@Controller
public class AssignmentController {

    private AssignmentService assignmentService;

    @Autowired
    public AssignmentController(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    @GetMapping("/")
    public String assignmentPage( Model model) {
        List<Assignment> result = assignmentService.getAllAssignments();
        model.addAttribute("assignments", result);
        return "allAssignments";
    }

    @GetMapping("/assignment/{assignmentId}")
    public String assignment(@PathVariable("assignmentId") Long id, Model model, HttpServletRequest request) {
        Assignment assignment = assignmentService.getAssignmentById(id);
        model.addAttribute("assignment", assignment);
        AnswerFromUserForm tryFromCookie = Utils.getAnswerFromJson("taskProgress" + id, request.getCookies());
        if (tryFromCookie == null) {
            tryFromCookie = new AnswerFromUserForm();
        }
        model.addAttribute("answer", tryFromCookie);
        if (tryFromCookie.getAnswer() != null) {
            model.addAttribute("userQueryResult", assignmentService.getResult(tryFromCookie.getAnswer()));
        }
        model.addAttribute("expectedResult", assignmentService.getResult(assignment.getCorrectQuery()));
        return "solveAssignmentPage";
    }

    @PostMapping("/check")
    public String checkAnswer(@ModelAttribute("answer") AnswerFromUserForm answer, HttpServletResponse response) {
        System.out.println("Result:"+assignmentService.checkResult(answer.getId(), answer.getAnswer()));
        answer.setCorrectAnswer(assignmentService.checkResult(answer.getId(), answer.getAnswer()));
        Cookie cookie = null;
        try {
            cookie = new Cookie("taskProgress" + answer.getId(), URLEncoder.encode(Utils.convertToJson(answer), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.addCookie(cookie);
        return "redirect:/assignment/" + answer.getId();
    }
}

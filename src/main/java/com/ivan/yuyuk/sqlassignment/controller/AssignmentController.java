package com.ivan.yuyuk.sqlassignment.controller;

import com.ivan.yuyuk.sqlassignment.entity.Assignment;
import com.ivan.yuyuk.sqlassignment.entity.Solution;
import com.ivan.yuyuk.sqlassignment.entity.SolutionId;
import com.ivan.yuyuk.sqlassignment.entity.User;
import com.ivan.yuyuk.sqlassignment.model.AnswerFromUserForm;
import com.ivan.yuyuk.sqlassignment.repository.SolutionRepository;
import com.ivan.yuyuk.sqlassignment.service.AssignmentService;
import com.ivan.yuyuk.sqlassignment.service.MyUserDetailService;
import com.ivan.yuyuk.sqlassignment.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

@Controller
public class AssignmentController {

    private final AssignmentService assignmentService;
    private final MyUserDetailService details;
    private final SolutionRepository solutionRepository;

    @Autowired
    public AssignmentController(AssignmentService assignmentService, MyUserDetailService details, SolutionRepository solutionRepository) {
        this.assignmentService = assignmentService;
        this.details = details;
        this.solutionRepository = solutionRepository;
    }

    @GetMapping("/")
    public String assignmentPage(Model model) {
        model.addAttribute("assignments", assignmentService.getAllAssignments());
        return "allAssignments";
    }

    @GetMapping("/assignment/{assignmentId}")
    public String assignment(@PathVariable("assignmentId") Long id, Model model, HttpServletRequest request) {
        User user = details.getUser();
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

    //возможно также использовать PathVariable.
    @PostMapping("/check")
    public String checkAnswer(@ModelAttribute("answer") AnswerFromUserForm answer, HttpServletResponse response) {
        boolean resultOfUserAnswer = assignmentService.checkResult(answer.getId(), answer.getAnswer());
        if(resultOfUserAnswer) {
            Solution solution = new Solution(new SolutionId(details.getUser().getId(),answer.getId()), answer.getAnswer());
            solution.setAssignment(assignmentService.getAssignmentById(answer.getId()));
            solution.setUser(details.getUser());
            solutionRepository.save(solution);
        }
        answer.setCorrectAnswer(resultOfUserAnswer);

        Cookie cookie = null;
        try {
            cookie = new Cookie("taskProgress" + answer.getId(), URLEncoder.encode(Utils.convertToJson(answer), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.addCookie(cookie);
        return "redirect:/assignment/" + answer.getId();
    }

    @GetMapping("/userProgress")
    public String getAll(Model model) {
        model.addAttribute("progress", solutionRepository.findSolutionsByUser_Id(details.getUser().getId()));
        return "userProgress";
    }
}

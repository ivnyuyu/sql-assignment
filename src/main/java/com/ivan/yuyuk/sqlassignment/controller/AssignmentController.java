package com.ivan.yuyuk.sqlassignment.controller;

import com.ivan.yuyuk.sqlassignment.repository.AssignmentDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AssignmentController {

    private AssignmentDAO assignmentDAO;

    @Autowired
    public AssignmentController(AssignmentDAO assignmentDAO) {
        this.assignmentDAO = assignmentDAO;
    }

    @GetMapping
    public String assignmentPage() {
        assignmentDAO.printConsoleAssignment();
        return "assignment";
    }
}

package com.ivan.yuyuk.sqlassignment.service;

import com.ivan.yuyuk.sqlassignment.entity.Assignment;
import com.ivan.yuyuk.sqlassignment.repository.AssignmentDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssignmentService {
    private AssignmentDAO assignmentDAO;

    @Autowired
    public AssignmentService(AssignmentDAO assignmentDAO) {
        this.assignmentDAO = assignmentDAO;
    }

    public List<Assignment> getAllAssignments() {
        return assignmentDAO.getAllAssignments();
    }

    public Assignment getAssignmentById(Long id) {
        return assignmentDAO.getAssignmentById(id);
    }

    public void checkUserQuery(Long idOfAssignment, String query) {
        Assignment assignment = assignmentDAO.getAssignmentById(idOfAssignment);
        List<Object[]> result = assignmentDAO.executeNativeQuery(assignment.getCorrectQuery());
        for (Object[] a : result) {
            System.out.println("По идее ID: " + a[0]);
        }
    }
}

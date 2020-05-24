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

    public List<Object[]> getResult(String text) {
        return assignmentDAO.executeNativeQuery(text);
    }

    public Assignment getAssignmentById(Long id) {
        return assignmentDAO.getAssignmentById(id);
    }

    public boolean checkUserQuery(Long idOfAssignment, String query) {
        List<Object[]> expectedResult;
        List<Object[]> userResult;
        try {
            Assignment assignment = assignmentDAO.getAssignmentById(idOfAssignment);
            expectedResult = assignmentDAO.executeNativeQuery(assignment.getCorrectQuery());
            userResult = assignmentDAO.executeNativeQuery(query);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        if (expectedResult.size() != userResult.size()) {
            return false;
        }
        for(int i = 0; i < expectedResult.size(); i++){
            if(expectedResult.get(i).length != userResult.get(i).length){
                return false;
            }
        }
        for (int i = 0; i < expectedResult.size(); i++) {
            for (int j = 0; j < expectedResult.get(i).length; j++) {
                Object expected = expectedResult.get(i)[j];
                Object userQuery = userResult.get(i)[j];
                if (!expected.equals(userQuery)) {
                    return false;
                }
            }
        }
        return true;
    }
}

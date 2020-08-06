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

    public boolean checkResult(Long id, String userQuery) {
        List<Object[]> userQueryResult = assignmentDAO.executeNativeQuery(userQuery);
        String correctQuery = assignmentDAO.getAssignmentById(id).getCorrectQuery();
        Object result = assignmentDAO.getEntityManager().createNativeQuery(String.format("SELECT IF(count1 = count2 AND count1 = count3, 'identical', 'mis-matched')\n" +
                "FROM\n" +
                "    (\n" +
                "        SELECT\n" +
                "            (SELECT COUNT(*) FROM (%s) as a) AS count1,\n" +
                "            (SELECT COUNT(*) FROM (%s) as b) AS count2,\n" +
                "            (SELECT COUNT(*) FROM (SELECT * FROM (%s) as c UNION SELECT * FROM (%s) as d) AS unioned) AS count3\n" +
                "    )\n" +
                "        AS counts",userQuery,correctQuery,userQuery,correctQuery)).getSingleResult();
        System.out.println(result);
        return result.equals("identical");
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
        for (int i = 0; i < expectedResult.size(); i++) {
            if (expectedResult.get(i).length != userResult.get(i).length) {
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

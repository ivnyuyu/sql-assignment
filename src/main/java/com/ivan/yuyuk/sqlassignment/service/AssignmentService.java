package com.ivan.yuyuk.sqlassignment.service;

import com.ivan.yuyuk.sqlassignment.entity.Assignment;
import com.ivan.yuyuk.sqlassignment.repository.AssignmentDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssignmentService {
    private final AssignmentDAO assignmentDAO;

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

}

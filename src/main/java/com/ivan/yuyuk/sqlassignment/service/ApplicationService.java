package com.ivan.yuyuk.sqlassignment.service;

import com.ivan.yuyuk.sqlassignment.entity.Assignment;
import com.ivan.yuyuk.sqlassignment.entity.Solution;
import com.ivan.yuyuk.sqlassignment.entity.SolutionId;
import com.ivan.yuyuk.sqlassignment.entity.User;
import com.ivan.yuyuk.sqlassignment.model.ResponseStatus;
import com.ivan.yuyuk.sqlassignment.repository.AssignmentDAO;
import com.ivan.yuyuk.sqlassignment.repository.SolutionRepository;
import com.ivan.yuyuk.sqlassignment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ApplicationService {

    private final AssignmentDAO assignmentDAO;
    private final MyUserDetailService userService;
    private final SolutionRepository solutionDAO;
    private final UserRepository userRepository;

    private final static List<String> FORBIDDEN_KEY = new ArrayList<String>() {{
       add("UPDATE");
       add("DELETE");
       add("INSERT");
    }};

    @Autowired
    public ApplicationService(AssignmentDAO assignmentDAO, MyUserDetailService userService,
                              SolutionRepository solutionDAO, UserRepository userRepository) {
        this.assignmentDAO = assignmentDAO;
        this.userService = userService;
        this.solutionDAO = solutionDAO;
        this.userRepository = userRepository;
    }

    public List<Assignment> getAllAssignments() {
        return assignmentDAO.getAllAssignments();
    }

    public Assignment getAssignmentById(Long id) {
        return assignmentDAO.getAssignmentById(id);
    }

    public User getCurrentUserProfile() {
        return userService.getUser();
    }

    public List<Object[]> getResult(String text) {
        return assignmentDAO.executeNativeQuery(text);
    }

    public boolean checkUserQuery(Long id, String userQuery) {
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

    public ResponseStatus saveUserCorrectAnswer(Long id, String userQuery) {
        try {
            if(isQueryForbidden(userQuery)){
                return ResponseStatus.FORBIDDEN_KEY_USE_ERROR;
            }
            try {
                assignmentDAO.executeNativeQuery(userQuery);
            } catch (Exception e) {
                return ResponseStatus.SYNTAX_ERROR;
            }
            //todo fix this
            if(checkUserQuery(id, userQuery.replace(";"," "))) {
                Solution solution = new Solution(new SolutionId(getCurrentUserProfile().getId(),id), userQuery);
                solution.setAssignment(getAssignmentById(id));
                solution.setUser(getCurrentUserProfile());
                solutionDAO.save(solution);
                return ResponseStatus.SUCCESS;
            }
        } catch (Exception e) {
            return ResponseStatus.NO_MATCH_RESULT_ERROR;
        }
        return ResponseStatus.NO_MATCH_RESULT_ERROR;
    }

    private boolean isQueryForbidden(String query) {
        if(query == null || query.trim().length() == 0) {
            return true;
        }
        String[] result = query.toUpperCase().trim().split(" ");
        for(String temp: result) {
            if(FORBIDDEN_KEY.contains(temp)) {
                return true;
            }
        }
        return false;
    }

    public List<Solution> findAllCorrectAssignmentsByCurrentUser() {
        return solutionDAO.findSolutionsByUser_Id(getCurrentUserProfile().getId());
    }

    public boolean doRegistration(User user) {
        Optional<User> userFromDB = userRepository.findByUserName(user.getUserName());
        if(!userFromDB.isPresent()) {
            user.setActive(true);
            user.setRoles("USER");
            userRepository.save(user);
        }
        return false;
    }
}
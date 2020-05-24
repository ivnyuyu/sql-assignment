package com.ivan.yuyuk.sqlassignment.repository;

import com.ivan.yuyuk.sqlassignment.entity.Assignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class AssignmentDAO {
    private EntityManager entityManager;

    @Autowired
    public AssignmentDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Assignment> getAllAssignments() {
        return entityManager.createQuery("select a from Assignment a", Assignment.class).getResultList();
    }

    public Assignment getAssignmentById(Long id) {
        return entityManager.find(Assignment.class, id);
    }

    public List<Object[]> executeNativeQuery(String query) {
        Query q = entityManager.createNativeQuery(query);
        List results = q.getResultList();
        if (results.isEmpty()) {
            return new ArrayList<>();
        }

        if (results.get(0) instanceof String) {
            return ((List<String>) results)
                    .stream()
                    .map(s -> new String[]{s})
                    .collect(Collectors.toList());
        }
        if (results.get(0) instanceof Integer) {
            return ((List<Integer>) results)
                    .stream()
                    .map(s -> new Integer[]{s})
                    .collect(Collectors.toList());
        } else {
            return (List<Object[]>) results;
        }
    }

    @Transactional
    public void printConsoleAssignment() {
        Query query = entityManager.createNativeQuery("select * from assignment;", Assignment.class);
        List<Assignment> result = query.getResultList();
        for (Assignment temp : result) {
            System.out.println(temp.getDescription() + ":" + temp.getCorrectQuery());
        }
    }
}

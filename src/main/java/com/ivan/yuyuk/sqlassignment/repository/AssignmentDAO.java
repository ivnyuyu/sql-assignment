package com.ivan.yuyuk.sqlassignment.repository;

import com.ivan.yuyuk.sqlassignment.entity.Assignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
public class AssignmentDAO {
    private EntityManager entityManager;

    @Autowired
    public AssignmentDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
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

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

    public List<Assignment> getAllAssignments() {
        return entityManager.createQuery("select a from Assignment a", Assignment.class).getResultList();
    }

    public Assignment getAssignmentById(Long id) {
        return entityManager.find(Assignment.class, id);
    }

    @Transactional(readOnly = true)
    public List<Object[]> executeNativeQuery(String query) {
        Query q = entityManager.createNativeQuery(query);
        return q.getResultList();
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

}

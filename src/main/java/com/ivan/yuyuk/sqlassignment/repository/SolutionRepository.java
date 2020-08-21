package com.ivan.yuyuk.sqlassignment.repository;

import com.ivan.yuyuk.sqlassignment.entity.Solution;
import com.ivan.yuyuk.sqlassignment.entity.SolutionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolutionRepository extends JpaRepository<Solution, SolutionId> {

    List<Solution> findSolutionsByUser_Id(Long id);
}

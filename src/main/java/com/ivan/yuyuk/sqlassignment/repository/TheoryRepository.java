package com.ivan.yuyuk.sqlassignment.repository;

import com.ivan.yuyuk.sqlassignment.entity.Theory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TheoryRepository extends JpaRepository<Theory, Long> {
}

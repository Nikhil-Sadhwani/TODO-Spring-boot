package com.assignment.TODO.repository;

import com.assignment.TODO.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query("SELECT t FROM Task t WHERE t.createdBy = :createdBy AND t.deleted = false")
    List<Task> findByCreatedByAndDeletedFalse(@Param("createdBy") Long createdBy);
}

package com.assignment.TODO.repository;

import com.assignment.TODO.entity.Subtask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubtaskRepository extends JpaRepository<Subtask, Long> {
    @Query("SELECT t FROM Subtask t WHERE t.taskId = :taskId AND t.deleted = false")
    List<Subtask> findByTaskIdAndDeletedFalse(@Param("taskId") Long taskId);
}

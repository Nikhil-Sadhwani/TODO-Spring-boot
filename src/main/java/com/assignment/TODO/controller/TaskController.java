package com.assignment.TODO.controller;

import com.assignment.TODO.entity.Task;
import com.assignment.TODO.config.JwtUtils;
import com.assignment.TODO.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;


@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private TaskService taskService;

    @PostMapping
    public ResponseEntity<String> createTask(@RequestBody Task taskDto, @RequestHeader("Authorization") String token) {
        try {
            Long userId = jwtUtils.validateAndGetUserFromToken(token);
            Task createdTask = taskService.createTask(taskDto, userId);

            return ResponseEntity.status(HttpStatus.CREATED).body("Task created successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to create the task. Please check your input and try again.");
        }

    }

    @GetMapping
    public ResponseEntity<?> getAllTasks(@RequestHeader("Authorization") String token) {
        try {
            Long userId = jwtUtils.validateAndGetUserFromToken(token);
            List<Task> tasks = taskService.getAllTasks(userId);

            if (tasks.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No tasks found.");
            }

            return ResponseEntity.status(HttpStatus.OK).body(tasks);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Failed to retrieve tasks. Invalid token or unauthorized access.");
        }
    }

    // Update task (by ID)
    @PutMapping("/{taskId}")
    public ResponseEntity<?> updateTask(@PathVariable Long taskId, @RequestBody Task taskDto, @RequestHeader("Authorization") String token) {
        try {
            Long userId = jwtUtils.validateAndGetUserFromToken(token);

            Task updatedTask = taskService.updateTask(taskId, taskDto, userId);
            if (updatedTask == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found or you are not authorized to update it.");
            }

            return ResponseEntity.status(HttpStatus.OK).body(updatedTask);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to update the task. Please check your input and try again.");
        }
    }

    // Soft delete task (by ID)
    @DeleteMapping("/{taskId}")
    public ResponseEntity<String> deleteTask(@PathVariable Long taskId, @RequestHeader("Authorization") String token) {
        try {
            Long userId = jwtUtils.validateAndGetUserFromToken(token);

            boolean isDeleted = taskService.softDeleteTask(taskId, userId);
            if (!isDeleted) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found, already deleted, or you are not authorized to delete it.");
            }

            return ResponseEntity.status(HttpStatus.OK).body("Task deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete the task. Please try again later.");
        }
    }
}


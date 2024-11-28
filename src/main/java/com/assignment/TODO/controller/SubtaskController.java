package com.assignment.TODO.controller;

import com.assignment.TODO.entity.Subtask;
import com.assignment.TODO.config.JwtUtils;
import com.assignment.TODO.service.SubtaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subtasks")
public class SubtaskController {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private SubtaskService subtaskService;

    // Create subtask
    @PostMapping
    public ResponseEntity<String> createSubtask(@RequestBody Subtask subtaskDto, @RequestHeader("Authorization") String token) {

        try {
            Long userId = jwtUtils.validateAndGetUserFromToken(token);
            Subtask createdSubtask = subtaskService.createSubtask(subtaskDto, userId);

            if (createdSubtask != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body("Subtask created successfully.");
            }

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to create the subtask. Please check your input and try again.");
        }
    }

    // Get Subtasks by Task ID
    @GetMapping("/{taskId}")
    public ResponseEntity<?> getAllSubtasks(@PathVariable Long taskId, @RequestHeader("Authorization") String token) {
        try {
            Long userId = jwtUtils.validateAndGetUserFromToken(token);
            List<Subtask> subtasks = subtaskService.getAllSubtasks(taskId);

            if (subtasks.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No subtasks found.");
            }

            return ResponseEntity.status(HttpStatus.OK).body(subtasks);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Failed to retrieve subtasks. Invalid token or unauthorized access.");
        }
    }

    // Update subtask status
    @PutMapping("/{subtaskId}")
    public ResponseEntity<?> updateSubtask(@PathVariable Long subtaskId, @RequestBody Subtask subtaskDto, @RequestHeader("Authorization") String token) {
        try {
            Long userId = jwtUtils.validateAndGetUserFromToken(token);
            Subtask updatedSubtask = subtaskService.updateSubtaskStatus(subtaskId, subtaskDto, userId);

            if (updatedSubtask != null) {
                return ResponseEntity.status(HttpStatus.OK).body(updatedSubtask);
            }

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to update the subtask. Please check your input and try again.");
        }
    }

    // Soft delete subtask
    @DeleteMapping("/{subtaskId}")
    public ResponseEntity<String> softDeleteSubtask(@PathVariable Long subtaskId, @RequestHeader("Authorization") String token) {
        try {
            Long userId = jwtUtils.validateAndGetUserFromToken(token);
            boolean deleted = subtaskService.softDeleteSubtask(subtaskId, userId);

            if (deleted) {
                return ResponseEntity.status(HttpStatus.OK).body("Subtask deleted successfully.");
            }

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Subtask not found.");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete the subtask. Please check parameters.");
        }
    }
}

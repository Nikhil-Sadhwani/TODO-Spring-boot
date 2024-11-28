package com.assignment.TODO.service;

import com.assignment.TODO.entity.Subtask;
import com.assignment.TODO.entity.Task;
import com.assignment.TODO.repository.SubtaskRepository;
import com.assignment.TODO.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubtaskService {

    @Autowired
    private SubtaskRepository subtaskRepository;

    @Autowired
    private TaskRepository taskRepository;

    // Create subtask
    public Subtask createSubtask(Subtask subtaskDto, Long userId) {
        Optional<Task> taskOpt = taskRepository.findById(subtaskDto.getTaskId());

        if (taskOpt.isPresent()) {
            return subtaskRepository.save(subtaskDto);
        }

        return null;
    }

    public List<Subtask> getAllSubtasks(Long taskId) {
        return subtaskRepository.findByTaskIdAndDeletedFalse(taskId);
    }

    // Update subtask status
    public Subtask updateSubtaskStatus(Long subtaskId, Subtask subtaskDto, Long userId) {
        Optional<Subtask> subtaskOpt = subtaskRepository.findById(subtaskId);

        if (subtaskOpt.isPresent()) {
            Subtask subtask = subtaskOpt.get();

            Optional<Task> taskOpt = taskRepository.findById(subtask.getTaskId());
            if (taskOpt.isPresent() && !taskOpt.get().getCreatedBy().equals(userId)) {
                return null;
            }

            subtask.setStatus(subtaskDto.getStatus());
            return subtaskRepository.save(subtask);

        }

        return null;
    }

    // Soft delete subtask
    public boolean softDeleteSubtask(Long subtaskId, Long userId) {
        Optional<Subtask> subtaskOpt = subtaskRepository.findById(subtaskId);

        if (subtaskOpt.isPresent()) {
            Subtask subtask = subtaskOpt.get();

            Optional<Task> taskOpt = taskRepository.findById(subtask.getTaskId());
            if (taskOpt.isPresent() && !taskOpt.get().getCreatedBy().equals(userId)) {
                return false; // Unauthorized user
            }

            subtask.setDeleted(true);
            subtaskRepository.save(subtask);
            return true;

        }

        return false;
    }
}

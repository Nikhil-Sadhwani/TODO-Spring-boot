package com.assignment.TODO.service;

import com.assignment.TODO.entity.Task;
import com.assignment.TODO.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public Task createTask(Task taskDto, Long userIdentifier) {
        Task task = new Task();
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setDueDate(taskDto.getDueDate());
        task.setPriority(taskDto.getPriority());
        task.setStatus(taskDto.getStatus());
        task.setCreatedBy(userIdentifier);
        return taskRepository.save(task);
    }

    public List<Task> getAllTasks(Long userIdentifier) {
        return taskRepository.findByCreatedByAndDeletedFalse(userIdentifier);
    }
    public Task updateTask(Long taskId, Task taskDto, Long userId) {
        Optional<Task> taskOpt = taskRepository.findById(taskId);

        if (taskOpt.isPresent()) {
            Task task = taskOpt.get();

            if (!task.getCreatedBy().equals(userId)) {
                return null;
            }


            task.setDueDate(taskDto.getDueDate());
            task.setStatus(taskDto.getStatus());

            return taskRepository.save(task);
        }

        return null;
    }

    // Soft delete task
    public boolean softDeleteTask(Long taskId, Long userId) {
        Optional<Task> taskOpt = taskRepository.findById(taskId);

        if (taskOpt.isPresent()) {
            Task task = taskOpt.get();


            if (!task.getCreatedBy().equals(userId)) {
                return false;
            }


            task.setDeleted(true);
            taskRepository.save(task);
            return true;
        }

        return false;
    }
}


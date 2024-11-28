package com.assignment.TODO.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "subtask")
public class Subtask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private Status status;

    private Long taskId;

    private boolean deleted = false;

    // Getters and setters

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}

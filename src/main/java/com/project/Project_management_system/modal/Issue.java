package com.project.Project_management_system.modal;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String title;
    private String description;
    private String status;
    private Long projectID;
    private String priority;
    private LocalDate dueDate;

    @ElementCollection(fetch = FetchType.LAZY)
    @JsonIgnore
    private List<String> tags = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignee_id")
    private User assignee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Project project;

    @OneToMany(
        mappedBy = "issue",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    @JsonIgnore
    private List<Comment> comments = new ArrayList<>();

    // Helper methods for managing relationships
    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setIssue(this);
    }

    public void removeComment(Comment comment) {
        comments.remove(comment);
        comment.setIssue(null);
    }
}
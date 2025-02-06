package com.project.Project_management_system.modal;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String name;
    private String description;
    private String category;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> tag = new ArrayList<>();

    @JsonIgnore
    @OneToOne(
        mappedBy = "project",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private Chat chat;

    @ManyToOne(fetch = FetchType.EAGER)
    private User owner;

    @OneToMany(
        mappedBy = "project",
        cascade = {CascadeType.PERSIST, CascadeType.MERGE},
        orphanRemoval = true,
        fetch = FetchType.EAGER
    )
    private List<Issue> issues = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "project_team",
        joinColumns = @JoinColumn(name = "project_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> team = new ArrayList<>();

    // Helper methods for managing relationships
    public void addIssue(Issue issue) {
        issues.add(issue);
        issue.setProject(this);
    }

    public void removeIssue(Issue issue) {
        issues.remove(issue);
        issue.setProject(null);
    }
}
package com.project.Project_management_system.modal;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssueDTO {

    private Long id;
    private String title;
    private String description;
    private String status;
    private Long projectId;
    private String priority;
    private LocalDate dueDate;
    private List<String> tags = new ArrayList<>();
    private Project project;
    private User assignee;

    // Constructor to populate DTO from Issue
    public IssueDTO(Issue issue) {
        this.id = issue.getId();
        this.title = issue.getTitle();
        this.description = issue.getDescription();
        this.status = issue.getStatus();
        this.projectId = issue.getProject().getId();
        this.priority = issue.getPriority();
        this.dueDate = issue.getDueDate();
        this.tags = issue.getTags();  // Add necessary fields (e.g., tags)
        this.project = issue.getProject();
        this.assignee = issue.getAssignee();
    }
}

package com.project.Project_management_system.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.Project_management_system.modal.Issue;
import com.project.Project_management_system.modal.Project;
import com.project.Project_management_system.modal.User;
import com.project.Project_management_system.repository.IssueRepository;
import com.project.Project_management_system.request.IssueRequest;

@Service
public class IssueServiceImpl implements IssueService {

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @Transactional(readOnly = true)
    @Override
    public Issue getIssueById(Long issueId) throws Exception {
        return issueRepository.findById(issueId)
            .orElseThrow(() -> new Exception("No issue found with issue ID: " + issueId));
    }

    @Transactional(readOnly = true)
    @Override
    public List<Issue> getIssueByProjectId(Long projectId) throws Exception {
        return issueRepository.findByProjectId(projectId);
    }

    @Transactional
    @Override
    public Issue createIssue(IssueRequest issueRequest, User user) throws Exception {
        Project project = projectService.getProjectById(issueRequest.getProjectId());

        Issue issue = new Issue();
        issue.setTitle(issueRequest.getTitle());
        issue.setDescription(issueRequest.getDescription());
        issue.setStatus(issueRequest.getStatus());
        issue.setPriority(issueRequest.getPriority());
        issue.setDueDate(issueRequest.getDueDate());
        issue.setProject(project);
        
        project.getIssues().add(issue);
        
        return issueRepository.save(issue);
    }

    @Transactional
    @Override
    public void deleteIssue(Long issueId, Long userId) throws Exception {
        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(() -> new Exception("Issue not found with ID: " + issueId));

        // Check authorization
        if (issue.getAssignee() != null && !issue.getAssignee().getId().equals(userId)) {
            throw new Exception("User not authorized to delete this issue");
        }

        // Remove from project's issues list
        Project project = issue.getProject();
        if (project != null) {
            project.getIssues().remove(issue);
        }

        // Remove from assignee if exists
        User assignee = issue.getAssignee();
        if (assignee != null) {
            issue.setAssignee(null);
        }

        // Clear comments
        issue.getComments().clear();
        
        // Delete the issue
        issueRepository.delete(issue);
        issueRepository.flush();
        
        // Verify deletion
        if (issueRepository.existsById(issueId)) {
            throw new Exception("Failed to delete issue");
        }
    }

    @Transactional
    @Override
    public Issue addUserToIssue(Long issueId, Long userId) throws Exception {
        User user = userService.findUserById(userId);
        Issue issue = getIssueById(issueId);
        issue.setAssignee(user);
        return issueRepository.save(issue);
    }

    @Transactional
    @Override
    public Issue updateStatus(Long issueId, String status) throws Exception {
        Issue issue = getIssueById(issueId);
        issue.setStatus(status);
        return issueRepository.save(issue);
    }
}
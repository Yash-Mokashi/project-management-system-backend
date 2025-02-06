package com.project.Project_management_system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.Project_management_system.modal.Issue;
import com.project.Project_management_system.modal.IssueDTO;
import com.project.Project_management_system.modal.User;
import com.project.Project_management_system.request.IssueRequest;
import com.project.Project_management_system.response.MessageResponse;
import com.project.Project_management_system.service.IssueService;
import com.project.Project_management_system.service.UserService;


@RestController

@RequestMapping("/api/issues")
public class IssueController {
    
    @Autowired
    private IssueService issueService;
    
    @Autowired
    private UserService userService;

    @GetMapping("/{issueId}")
    public ResponseEntity<Issue> getIssueById(@PathVariable Long issueId) throws Exception {
        return ResponseEntity.ok(issueService.getIssueById(issueId));
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<Issue>> getIssueByProjectId(@PathVariable Long projectId) throws Exception {
        return ResponseEntity.ok(issueService.getIssueByProjectId(projectId));
    }

    @PostMapping
    public ResponseEntity<IssueDTO> createIssue(
            @RequestBody IssueRequest issue,
            @RequestHeader("Authorization") String token) throws Exception {
        User tokenUser = userService.findUserProfileByJwt(token);
        Issue createdIssue = issueService.createIssue(issue, tokenUser);
        
        IssueDTO issueDTO = new IssueDTO();
        issueDTO.setId(createdIssue.getId());
        issueDTO.setTitle(createdIssue.getTitle());
        issueDTO.setDescription(createdIssue.getDescription());
        issueDTO.setStatus(createdIssue.getStatus());
        issueDTO.setPriority(createdIssue.getPriority());
        issueDTO.setDueDate(createdIssue.getDueDate());
        issueDTO.setTags(createdIssue.getTags());
        issueDTO.setProject(createdIssue.getProject());
        issueDTO.setAssignee(createdIssue.getAssignee());
        
        return ResponseEntity.ok(issueDTO);
    }

    @DeleteMapping("/{issueId}")
    public ResponseEntity<MessageResponse> deleteIssue(
            @PathVariable Long issueId,
            @RequestHeader("Authorization") String token) throws Exception {
        User user = userService.findUserProfileByJwt(token);
        
        issueService.deleteIssue(issueId, user.getId());
        
        // Verify deletion before sending response
        Issue deletedIssue = null;
        try {
            deletedIssue = issueService.getIssueById(issueId);
        } catch (Exception e) {
            // Expected - issue should not be found
        }
        
        MessageResponse res = new MessageResponse();
        if (deletedIssue == null) {
            res.setMessage("Issue deleted successfully");
            return ResponseEntity.ok(res);
        } else {
            res.setMessage("Failed to delete issue");
            return ResponseEntity.internalServerError().body(res);
        }
    }

    @PutMapping("/{issueId}/assignee/{userId}")
    public ResponseEntity<Issue> addUserToIssue(
            @PathVariable Long issueId,
            @PathVariable Long userId) throws Exception {
        Issue issue = issueService.addUserToIssue(issueId, userId);
        return ResponseEntity.ok(issue);
    }

    @PutMapping("/{issueId}/status/{status}")
    public ResponseEntity<Issue> updateIssueStatus(
            @PathVariable Long issueId,
            @PathVariable String status) throws Exception {
        Issue issue = issueService.updateStatus(issueId, status);
        return ResponseEntity.ok(issue);
    }
}
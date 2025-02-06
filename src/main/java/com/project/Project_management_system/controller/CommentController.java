package com.project.Project_management_system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.Project_management_system.modal.Comment;
import com.project.Project_management_system.modal.User;
import com.project.Project_management_system.request.CreateCommentRequest;
import com.project.Project_management_system.response.MessageResponse;
import com.project.Project_management_system.service.CommentService;
import com.project.Project_management_system.service.UserService;


@RestController

@RequestMapping("/api/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;
    
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Comment> createComment(
            @RequestBody CreateCommentRequest req,
            @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Comment createdComment = commentService.createComment(
                req.getIssueId(),
                user.getId(),
                req.getContent());
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<MessageResponse> deleteComment(
            @PathVariable Long commentId,
            @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        commentService.deleteComment(commentId, user.getId());
        
        MessageResponse res = new MessageResponse();
        res.setMessage("Comment deleted successfully");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/{issueId}")
    public ResponseEntity<List<Comment>> getCommentByIssueId(@PathVariable Long issueId) {
        List<Comment> comments = commentService.findCommentByIssueId(issueId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }
}
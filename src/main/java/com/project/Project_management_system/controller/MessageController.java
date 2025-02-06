package com.project.Project_management_system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.Project_management_system.modal.Chat;
import com.project.Project_management_system.modal.Message;
import com.project.Project_management_system.modal.User;
import com.project.Project_management_system.request.CreateMessageRequest;
import com.project.Project_management_system.service.MessageService;
import com.project.Project_management_system.service.ProjectService;
import com.project.Project_management_system.service.UserService;


@RestController

@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private ProjectService projectService;

    // Endpoint to send a message
    @PostMapping("/send")
    public ResponseEntity<Message> sendMessage(@RequestBody CreateMessageRequest request) throws Exception {
        User user = userService.findUserById(request.getSenderId());
        Chat chat = projectService.getChatByProjectId(request.getProjectId());

        if (chat == null) {
            throw new Exception("Chat not found");
        }

        Message sentMessage = messageService.sendMessage(request.getSenderId(), request.getProjectId(), request.getContent());
        return ResponseEntity.ok(sentMessage);
    }

    // Endpoint to get all messages for a project
    @GetMapping("/{projectId}")
    public ResponseEntity<List<Message>> getMessagesByProjectId(@PathVariable Long projectId) throws Exception {
        List<Message> messages = messageService.getMessagesByProjectId(projectId);
        return ResponseEntity.ok(messages);
    }

    // New endpoint to get a specific message by its ID
    @GetMapping("/chat/{id}")
    public ResponseEntity<Message> getMessage(@PathVariable("id") Long id) throws Exception {
        Message message = messageService.getMessageById(id); // Use the updated method name here
        return ResponseEntity.ok(message);
    }
}

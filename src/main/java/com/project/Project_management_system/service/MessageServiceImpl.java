package com.project.Project_management_system.service;

import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.Project_management_system.modal.Chat;
import com.project.Project_management_system.modal.Message;
import com.project.Project_management_system.modal.User;
import com.project.Project_management_system.repository.MessageRepository;
import com.project.Project_management_system.repository.UserRepository;
import com.project.Project_management_system.service.ProjectService;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectService projectService;

    @Override
    public Message sendMessage(Long senderId, Long projectId, String content) throws Exception {
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new Exception("User not found with id: " + senderId));

        Chat chat = projectService.getChatByProjectId(projectId);
        if (chat == null) {
            throw new Exception("Chat not found for project ID: " + projectId);
        }

        Message message = new Message();
        message.setContent(content);
        message.setSender(sender);
        message.setCreatedAt(LocalDateTime.now());
        message.setChat(chat);

        Message savedMessage = messageRepository.save(message);
        chat.getMessages().add(savedMessage);

        return savedMessage;
    }

    @Override
    public List<Message> getMessagesByProjectId(Long projectId) throws Exception {
        Chat chat = projectService.getChatByProjectId(projectId);
        if (chat == null) {
            throw new Exception("Chat not found for project ID: " + projectId);
        }

        return messageRepository.findByChatIdOrderByCreatedAtAsc(chat.getId());
    }

    @Override
    public Message getMessageById(Long id) throws Exception {
        Optional<Message> message = messageRepository.findById(id);
        if (!message.isPresent()) {
            throw new Exception("Message not found with ID: " + id);
        }
        return message.get();
    }
}

package com.project.Project_management_system.service;

import java.util.List;
import com.project.Project_management_system.modal.Message;

public interface MessageService {

    // Method to send a message
    Message sendMessage(Long senderId, Long chatId, String content) throws Exception;
    
    // Method to get messages by project ID
    List<Message> getMessagesByProjectId(Long projectId) throws Exception;
    
    // Method to get a message by its ID
    Message getMessageById(Long id) throws Exception; // Add this method to fetch a message by ID
}

package com.project.Project_management_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.Project_management_system.modal.Chat;
import com.project.Project_management_system.repository.ChatRepository;

@Service
public class ChatServiceImpl implements ChatService{

	@Autowired
	private ChatRepository chatRepository;
	
	@Override
	public Chat createChat(Chat chat) {
		// TODO Auto-generated method stub
		return chatRepository.save(chat);
	}

}

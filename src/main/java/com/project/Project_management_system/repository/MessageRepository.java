package com.project.Project_management_system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.Project_management_system.modal.Message;

public interface MessageRepository extends JpaRepository<Message, Long>{

	List<Message> findByChatIdOrderByCreatedAtAsc(Long chatId);
}
 
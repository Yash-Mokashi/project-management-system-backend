package com.project.Project_management_system.service;

import com.project.Project_management_system.modal.Invitation;

import jakarta.mail.MessagingException;

public interface InvitationService {

	public void sendInvitation(String email, Long projectId) throws MessagingException;
	public Invitation acceptInvitation(String taken, Long userId) throws Exception;
	
	public String getTokenByuserMail(String userEmail);
	
	void deleteToken(String token);
}

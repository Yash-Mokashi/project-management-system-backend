package com.project.Project_management_system.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.Project_management_system.modal.Invitation;
import com.project.Project_management_system.repository.InvitationRepository;

import jakarta.mail.MessagingException;


@Service
public class InvitationServiceImpl implements InvitationService{
 
	@Autowired
	private InvitationRepository invitationRepository;
	
	@Autowired
	private EmailService emailService;
	
	@Override
	public void sendInvitation(String email, Long projectId) throws MessagingException {
		// TODO Auto-generated method stub
		String invitationToken =  UUID.randomUUID().toString();
		
		Invitation invitation = new Invitation();
		invitation.setEmail(email);
		invitation.setProjectId(projectId);
		invitation.setToken(invitationToken);
		
		invitationRepository.save(invitation);
		
		String invitationLink = "http://localhost:8080/accept_invitation?token="+invitationToken;
		emailService.sendEmailWithToken(email, invitationLink);
		
		
	}

	@Override
	public Invitation acceptInvitation(String taken, Long userId) throws Exception {
		// TODO Auto-generated method stub
		Invitation invitation = invitationRepository.findByToken(taken);
		if(invitation == null) {
			throw new Exception("invalid invitation token");
		}
		return invitation;
	}

	@Override
	public String getTokenByuserMail(String userEmail) {
		// TODO Auto-generated method stub
		Invitation invitation = invitationRepository.findByEmail(userEmail);
		return invitation.getToken();
	}

	@Override
	public void deleteToken(String token) {
		// TODO Auto-generated method stub
		Invitation invitation = invitationRepository.findByToken(token);
		invitationRepository.delete(invitation);
	}

	
}

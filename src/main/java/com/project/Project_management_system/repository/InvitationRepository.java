package com.project.Project_management_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.Project_management_system.modal.Invitation;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {

	Invitation findByToken(String token);
	
	Invitation findByEmail(String userEmail);
}

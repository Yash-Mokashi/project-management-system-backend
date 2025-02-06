package com.project.Project_management_system.service;

import jakarta.mail.MessagingException;

public interface EmailService {

	void sendEmailWithToken(String userEmail, String link)throws MessagingException;
}

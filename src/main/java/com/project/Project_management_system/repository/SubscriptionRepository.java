package com.project.Project_management_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.Project_management_system.modal.Subscription;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

	Subscription findByuserId(Long userId);
	
}

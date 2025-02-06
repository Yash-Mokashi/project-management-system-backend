package com.project.Project_management_system.service;

import com.project.Project_management_system.modal.PlanType;
import com.project.Project_management_system.modal.Subscription;
import com.project.Project_management_system.modal.User;

public interface SubscriptionService {

	Subscription creaSubscription(User user);
	
	Subscription getUserSubscription(Long userId) throws Exception;
	
	Subscription upgradeSubscription(Long userId, PlanType planType);
	
	boolean isValid(Subscription subscription);
}

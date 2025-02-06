package com.project.Project_management_system.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.Project_management_system.modal.PlanType;
import com.project.Project_management_system.modal.Subscription;
import com.project.Project_management_system.modal.User;
import com.project.Project_management_system.repository.SubscriptionRepository;

@Service
public class SubscriptoinServiceImpl implements SubscriptionService{

	@Autowired
	private UserService userService;
	
	@Autowired
	private SubscriptionRepository subscriptionRepository;
	@Override
	public Subscription creaSubscription(User user) {
		// TODO Auto-generated method stub
		Subscription subscription = new Subscription();
		subscription.setUser(user);
		subscription.setSubscriptionStartDate(LocalDate.now());
		subscription.setGetSubscriptionEndDate(LocalDate.now().plusMonths(12));
		subscription.setValid(true);
		subscription.setPlanType(PlanType.FREE);
		return subscriptionRepository.save(subscription);
	}

	@Override
	public Subscription getUserSubscription(Long userId) throws Exception {
		// TODO Auto-generated method stub
		Subscription subscription = subscriptionRepository.findByuserId(userId);
		if (!isValid(subscription)) {
			subscription.setPlanType(PlanType.FREE);
			subscription.setGetSubscriptionEndDate(LocalDate.now().plusMonths(12));
			subscription.setSubscriptionStartDate(LocalDate.now());
		}
		return subscriptionRepository.save(subscription);
	}

	@Override
	public Subscription upgradeSubscription(Long userId, PlanType planType) {
		// TODO Auto-generated method stub
		Subscription subscription = subscriptionRepository.findByuserId(userId);
		subscription.setPlanType(planType);
		subscription.setSubscriptionStartDate(LocalDate.now());
		if (planType.equals(planType.ANNUALLY)) {
			subscription.setGetSubscriptionEndDate(LocalDate.now().plusMonths(12));
		}else {
			subscription.setGetSubscriptionEndDate(LocalDate.now().plusMonths(1));
		}
		return subscriptionRepository.save(subscription);
	}

	@Override
	public boolean isValid(Subscription subscription) {
		// TODO Auto-generated method stub
		if (subscription.getPlanType().equals(PlanType.FREE)) {
			return true;
		}
		LocalDate enDate = subscription.getGetSubscriptionEndDate();
		LocalDate currDate = LocalDate.now();
		return enDate.isAfter(currDate) || enDate.isEqual(currDate);
	}

}

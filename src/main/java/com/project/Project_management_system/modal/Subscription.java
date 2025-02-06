package com.project.Project_management_system.modal;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subscription {

	@Id
	@GeneratedValue(strategy =  GenerationType.AUTO)
	private Long id;
	
	private LocalDate subscriptionStartDate;
	
	private LocalDate getSubscriptionEndDate;
	
	private PlanType planType;
	
	private boolean isValid;
	
	@OneToOne
	private User user;
	
	
}

package com.project.Project_management_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class ProjectManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectManagementSystemApplication.class, args);
	}

}

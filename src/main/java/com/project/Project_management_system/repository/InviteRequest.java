package com.project.Project_management_system.repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InviteRequest {

	private Long projectId;
	
	private String email;
	
	
}

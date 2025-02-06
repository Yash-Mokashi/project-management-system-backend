package com.project.Project_management_system.request;

import lombok.Data;

@Data
public class CreateCommentRequest {

	private Long issueId;
	
	private String content;
}

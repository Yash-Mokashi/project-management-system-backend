package com.project.Project_management_system.service;

import java.util.List;


import com.project.Project_management_system.modal.Issue;
import com.project.Project_management_system.modal.User;
import com.project.Project_management_system.request.IssueRequest;

public interface IssueService {

	Issue getIssueById(Long issueId) throws Exception;
	
	List<Issue> getIssueByProjectId(Long projectId)throws Exception;
	
	Issue createIssue(IssueRequest issue, User user) throws Exception;
	
	void deleteIssue(Long issueId, Long userid) throws Exception;
	
	Issue addUserToIssue(Long issueId, Long userId) throws Exception;
	
	Issue updateStatus(Long issueId, String status)throws Exception;
}

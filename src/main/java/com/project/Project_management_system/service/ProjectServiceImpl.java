package com.project.Project_management_system.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.Project_management_system.modal.Chat;
import com.project.Project_management_system.modal.Project;
import com.project.Project_management_system.modal.User;
import com.project.Project_management_system.repository.ProjectRepository;
import org.hibernate.Hibernate;

@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private ChatService chatService;
    
    @Override
    @Transactional
    public Project createProject(Project project, User user) throws Exception {
        Project createdProject = new Project();
        
        createdProject.setOwner(user);
        createdProject.setTag(project.getTag());
        createdProject.setName(project.getName());
        createdProject.setCategory(project.getCategory());
        createdProject.setDescription(project.getDescription());
        createdProject.getTeam().add(user);
        
        Project savedProject = projectRepository.save(createdProject);
        
        Chat chat = new Chat();
        chat.setProject(savedProject);
        
        Chat projectChat = chatService.createChat(chat);
        savedProject.setChat(projectChat);
        
        return savedProject;
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Project> getProjectByTeam(User user, String category, String tag) throws Exception {
        List<Project> projects = projectRepository.findByTeamContainingOrOwner(user, user);
        
        // Initialize the collections to prevent LazyInitializationException
        for (Project project : projects) {
            Hibernate.initialize(project.getIssues());
            project.getIssues().forEach(issue -> 
                Hibernate.initialize(issue.getTags())
            );
        }
        
        if(category != null) {
            projects = projects.stream()
                .filter(project -> project.getCategory().equals(category))
                .collect(Collectors.toList());
        }
        
        if(tag != null) {
            projects = projects.stream()
                .filter(project -> project.getTag().contains(tag))
                .collect(Collectors.toList());
        }
        
        return projects;
    }

    @Override
    @Transactional(readOnly = true)
    public Project getProjectById(Long projectId) throws Exception {
        Optional<Project> optionalProject = projectRepository.findById(projectId);
        if(optionalProject.isEmpty()) {
            throw new Exception("project not found");
        }
        Project project = optionalProject.get();
        
        // Initialize collections
        Hibernate.initialize(project.getIssues());
        project.getIssues().forEach(issue -> 
            Hibernate.initialize(issue.getTags())
        );
        
        return project;
    }

    @Override
    @Transactional
    public void deleteProject(Long projectId, Long userId) throws Exception {
        Project project = getProjectById(projectId);
        // Check if user is owner before deleting
        if (!project.getOwner().getId().equals(userId)) {
            throw new Exception("Only project owner can delete the project");
        }
        projectRepository.deleteById(projectId);
    }

    @Override
    @Transactional
    public Project updateProject(Project updatedProject, Long id) throws Exception {
        Project project = getProjectById(id);
        
        if (updatedProject.getName() != null) {
            project.setName(updatedProject.getName());
        }
        if (updatedProject.getDescription() != null) {
            project.setDescription(updatedProject.getDescription());
        }
        if (updatedProject.getTag() != null) {
            project.setTag(updatedProject.getTag());
        }
        
        return projectRepository.save(project);
    }

    @Override
    @Transactional
    public void addUserToProject(Long projectId, Long userId) throws Exception {
        Project project = getProjectById(projectId);
        User user = userService.findUserById(userId);
        if(!project.getTeam().contains(user)) {
            project.getChat().getUsers().add(user);
            project.getTeam().add(user);
        }
        System.out.println(project);
        projectRepository.save(project);
    }

    @Override
    @Transactional
    public void removeUserFromProject(Long projectId, Long userId) throws Exception {
        Project project = getProjectById(projectId);
        User user = userService.findUserById(userId);
        
        // Prevent removing the owner
        if(project.getOwner().getId().equals(userId)) {
            throw new Exception("Cannot remove project owner from the project");
        }
        
        if(project.getTeam().contains(user)) {
            project.getChat().getUsers().remove(user);
            project.getTeam().remove(user);
        }
        projectRepository.save(project);
    }

    @Override
    @Transactional(readOnly = true)
    public Chat getChatByProjectId(Long projectId) throws Exception {
        Project project = getProjectById(projectId);
        Hibernate.initialize(project.getChat().getUsers());
        return project.getChat();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Project> searchProject(String keyword, User user) throws Exception {
        if (keyword == null || keyword.isEmpty()) {
            throw new Exception("Keyword cannot be empty");
        }
        
        System.out.println("Debug - Search keyword: " + keyword);
        System.out.println("Debug - User ID: " + user.getId());
        
        try {
            List<Project> projects = projectRepository.searchByKeywordAndTeam(keyword, user.getId());
            System.out.println("Debug - Found projects: " + projects.size());
            
            for (Project project : projects) {
                System.out.println("Debug - Project found: " + project.getName());
            }
            
            return projects;
        } catch (Exception e) {
            System.out.println("Debug - Error occurred: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }


}
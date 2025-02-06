package com.project.Project_management_system.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.project.Project_management_system.modal.Project;
import com.project.Project_management_system.modal.User;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    // Simple name-only search
    @Query("SELECT p FROM Project p WHERE LOWER(p.name) LIKE LOWER(concat('%', :keyword, '%'))")
    List<Project> searchByKeywordAndTeam(@Param("keyword") String keyword, @Param("userId") Long userId);

    List<Project> findByTeamContainingOrOwner(User user, User owner);
}
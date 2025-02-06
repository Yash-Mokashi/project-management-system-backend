package com.project.Project_management_system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.Project_management_system.modal.Issue;

public interface IssueRepository extends JpaRepository<Issue, Long> {

    List<Issue> findByProjectId(Long id);

    // Custom query to eagerly load tags with JOIN FETCH
    @Query("SELECT i FROM Issue i JOIN FETCH i.tags WHERE i.project.id = :projectId")
    List<Issue> findByProjectIdWithTags(@Param("projectId") Long projectId);
}

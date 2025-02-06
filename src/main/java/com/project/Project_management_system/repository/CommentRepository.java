package com.project.Project_management_system.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.project.Project_management_system.modal.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByIssueId(Long issueId);
}

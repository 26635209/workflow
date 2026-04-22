package com.workflow.backend.repository;

import com.workflow.backend.model.Workflow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkflowRepository extends JpaRepository<Workflow, Long> {
    
    List<Workflow> findByCreatorIdOrderByCreatedAtDesc(Long creatorId);
    
    List<Workflow> findByStatusOrderByCreatedAtDesc(String status);
    
    List<Workflow> findByCreatorIdAndStatus(Long creatorId, String status);
}

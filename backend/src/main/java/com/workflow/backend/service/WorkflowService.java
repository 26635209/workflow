package com.workflow.backend.service;

import com.workflow.backend.dto.WorkflowRequest;
import com.workflow.backend.model.Workflow;
import java.util.List;

public interface WorkflowService {
    
    Workflow create(WorkflowRequest request);
    
    Workflow update(Long id, WorkflowRequest request);
    
    void delete(Long id);
    
    Workflow getById(Long id);
    
    List<Workflow> getAll();
    
    List<Workflow> getByCreatorId(Long creatorId);
    
    List<Workflow> getByStatus(String status);
}

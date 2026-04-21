package com.workflow.backend.service;

import com.workflow.backend.model.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class WorkflowService {

    private final Map<String, WorkflowResponse> workflows = new HashMap<>();
    private final AtomicInteger idCounter = new AtomicInteger(1);

    public List<WorkflowResponse> getAllWorkflows() {
        return new ArrayList<>(workflows.values());
    }

    public WorkflowResponse getWorkflowById(String id) {
        return workflows.get(id);
    }

    public WorkflowResponse createWorkflow(WorkflowRequest request) {
        String id = "WF-" + idCounter.getAndIncrement();
        WorkflowResponse response = new WorkflowResponse();
        response.setId(id);
        response.setName(request.getName());
        response.setDescription(request.getDescription());
        response.setStatus("draft");
        response.setCreatedAt(LocalDateTime.now());
        response.setUpdatedAt(LocalDateTime.now());
        
        List<StepResponse> steps = new ArrayList<>();
        for (StepRequest step : request.getSteps()) {
            StepResponse stepResponse = new StepResponse();
            stepResponse.setName(step.getName());
            stepResponse.setAction(step.getAction());
            stepResponse.setDescription(step.getDescription());
            stepResponse.setStatus("pending");
            steps.add(stepResponse);
        }
        response.setSteps(steps);
        
        workflows.put(id, response);
        return response;
    }

    public WorkflowResponse updateWorkflow(String id, WorkflowRequest request) {
        WorkflowResponse existing = workflows.get(id);
        if (existing == null) {
            throw new RuntimeException("Workflow not found");
        }
        
        existing.setName(request.getName());
        existing.setDescription(request.getDescription());
        existing.setUpdatedAt(LocalDateTime.now());
        
        List<StepResponse> steps = new ArrayList<>();
        for (StepRequest step : request.getSteps()) {
            StepResponse stepResponse = new StepResponse();
            stepResponse.setName(step.getName());
            stepResponse.setAction(step.getAction());
            stepResponse.setDescription(step.getDescription());
            stepResponse.setStatus("pending");
            steps.add(stepResponse);
        }
        existing.setSteps(steps);
        
        return existing;
    }

    public void deleteWorkflow(String id) {
        workflows.remove(id);
    }

    public WorkflowResponse executeWorkflow(String id) {
        WorkflowResponse workflow = workflows.get(id);
        if (workflow == null) {
            throw new RuntimeException("Workflow not found");
        }
        
        workflow.setStatus("running");
        workflow.setUpdatedAt(LocalDateTime.now());
        
        for (StepResponse step : workflow.getSteps()) {
            step.setStatus("executing");
            step.setExecutedAt(LocalDateTime.now());
            
            // Simulate step execution
            try {
                Thread.sleep(100);
                step.setStatus("completed");
            } catch (InterruptedException e) {
                step.setStatus("failed");
            }
        }
        
        workflow.setStatus("completed");
        workflow.setUpdatedAt(LocalDateTime.now());
        
        return workflow;
    }
}

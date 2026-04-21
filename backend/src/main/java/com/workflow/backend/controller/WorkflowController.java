package com.workflow.backend.controller;

import com.workflow.backend.model.*;
import com.workflow.backend.service.WorkflowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workflows")
@CrossOrigin(origins = "*")
public class WorkflowController {

    @Autowired
    private WorkflowService workflowService;

    @GetMapping
    public ResponseEntity<List<WorkflowResponse>> getAllWorkflows() {
        return ResponseEntity.ok(workflowService.getAllWorkflows());
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkflowResponse> getWorkflowById(@PathVariable String id) {
        return ResponseEntity.ok(workflowService.getWorkflowById(id));
    }

    @PostMapping
    public ResponseEntity<WorkflowResponse> createWorkflow(@RequestBody WorkflowRequest request) {
        return ResponseEntity.ok(workflowService.createWorkflow(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkflowResponse> updateWorkflow(
            @PathVariable String id, 
            @RequestBody WorkflowRequest request) {
        return ResponseEntity.ok(workflowService.updateWorkflow(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkflow(@PathVariable String id) {
        workflowService.deleteWorkflow(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/execute")
    public ResponseEntity<WorkflowResponse> executeWorkflow(@PathVariable String id) {
        return ResponseEntity.ok(workflowService.executeWorkflow(id));
    }
}

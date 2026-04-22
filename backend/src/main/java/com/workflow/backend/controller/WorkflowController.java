package com.workflow.backend.controller;

import com.workflow.backend.dto.WorkflowRequest;
import com.workflow.backend.model.Workflow;
import com.workflow.backend.service.WorkflowService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/workflows")
public class WorkflowController {

    @Autowired
    private WorkflowService workflowService;

    @GetMapping
    public ResponseEntity<List<Workflow>> getAll() {
        return ResponseEntity.ok(workflowService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Workflow> getById(@PathVariable Long id) {
        return ResponseEntity.ok(workflowService.getById(id));
    }

    @GetMapping("/creator/{creatorId}")
    public ResponseEntity<List<Workflow>> getByCreatorId(@PathVariable Long creatorId) {
        return ResponseEntity.ok(workflowService.getByCreatorId(creatorId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Workflow>> getByStatus(@PathVariable String status) {
        return ResponseEntity.ok(workflowService.getByStatus(status));
    }

    @PostMapping
    public ResponseEntity<Workflow> create(@Valid @RequestBody WorkflowRequest request) {
        return ResponseEntity.ok(workflowService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Workflow> update(@PathVariable Long id, @Valid @RequestBody WorkflowRequest request) {
        return ResponseEntity.ok(workflowService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Long id) {
        workflowService.delete(id);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "删除成功");
        return ResponseEntity.ok(response);
    }
}

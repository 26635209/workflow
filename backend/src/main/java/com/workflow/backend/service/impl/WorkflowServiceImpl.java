package com.workflow.backend.service.impl;

import com.workflow.backend.dto.WorkflowRequest;
import com.workflow.backend.model.Workflow;
import com.workflow.backend.repository.WorkflowRepository;
import com.workflow.backend.service.WorkflowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class WorkflowServiceImpl implements WorkflowService {

    @Autowired
    private WorkflowRepository workflowRepository;

    @Override
    public Workflow create(WorkflowRequest request) {
        Workflow workflow = new Workflow();
        workflow.setName(request.getName());
        workflow.setDescription(request.getDescription());
        workflow.setStatus(request.getStatus() != null ? request.getStatus() : "draft");
        workflow.setPriority(request.getPriority() != null ? request.getPriority() : 0);
        workflow.setCreatorId(request.getCreatorId());
        return workflowRepository.save(workflow);
    }

    @Override
    public Workflow update(Long id, WorkflowRequest request) {
        Workflow workflow = workflowRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("流程不存在"));
        workflow.setName(request.getName());
        workflow.setDescription(request.getDescription());
        if (request.getStatus() != null) {
            workflow.setStatus(request.getStatus());
        }
        if (request.getPriority() != null) {
            workflow.setPriority(request.getPriority());
        }
        return workflowRepository.save(workflow);
    }

    @Override
    public void delete(Long id) {
        workflowRepository.deleteById(id);
    }

    @Override
    public Workflow getById(Long id) {
        return workflowRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("流程不存在"));
    }

    @Override
    public List<Workflow> getAll() {
        return workflowRepository.findAll();
    }

    @Override
    public List<Workflow> getByCreatorId(Long creatorId) {
        return workflowRepository.findByCreatorIdOrderByCreatedAtDesc(creatorId);
    }

    @Override
    public List<Workflow> getByStatus(String status) {
        return workflowRepository.findByStatusOrderByCreatedAtDesc(status);
    }
}

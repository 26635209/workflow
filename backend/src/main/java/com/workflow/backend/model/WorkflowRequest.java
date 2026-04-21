package com.workflow.backend.model;

import lombok.Data;

import java.util.List;

@Data
public class WorkflowRequest {
    private String name;
    private String description;
    private List<StepRequest> steps;
}

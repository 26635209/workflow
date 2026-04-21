package com.workflow.backend.model;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class WorkflowResponse {
    private String id;
    private String name;
    private String description;
    private List<StepResponse> steps;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

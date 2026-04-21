package com.workflow.backend.model;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class StepResponse {
    private String name;
    private String action;
    private String description;
    private String status;
    private LocalDateTime executedAt;
}

package com.workflow.backend.model;

import lombok.Data;

@Data
public class StepRequest {
    private String name;
    private String action;
    private String description;
}

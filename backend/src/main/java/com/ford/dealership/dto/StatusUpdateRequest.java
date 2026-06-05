package com.ford.dealership.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class StatusUpdateRequest {
    /** "pendente", "aprovado" ou "rejeitado". */
    @NotBlank
    private String status;
}

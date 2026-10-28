package com.fairmarket.resaleservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ResaleQueueRequestDTO {

    @NotBlank(message = "El nombre de la cola de reventa no puede estar vacío")
    private String queueName;

    private boolean durable = true;
}
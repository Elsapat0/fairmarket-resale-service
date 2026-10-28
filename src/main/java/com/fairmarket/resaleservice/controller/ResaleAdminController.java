package com.fairmarket.resaleservice.controller;

import com.fairmarket.resaleservice.dto.ResaleQueueRequestDTO;
import com.fairmarket.resaleservice.service.RabbitAdminService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/queues")
public class ResaleAdminController {

    private final RabbitAdminService rabbitAdminService;

    public ResaleAdminController(RabbitAdminService rabbitAdminService) {
        this.rabbitAdminService = rabbitAdminService;
    }

    @PostMapping
    public ResponseEntity<String> createQueue(@Valid @RequestBody ResaleQueueRequestDTO dto) {
        rabbitAdminService.createResaleQueue(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Cola de reventa/lista de espera '" + dto.getQueueName() + "' creada exitosamente.");
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<String> deleteQueue(@PathVariable("name") String name) {
        boolean deleted = rabbitAdminService.deleteQueue(name);
        if (deleted) {
            return ResponseEntity.ok("Cola de reventa '" + name + "' eliminada correctamente.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se pudo eliminar la cola o no existía.");
    }
}
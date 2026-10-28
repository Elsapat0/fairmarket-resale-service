package com.fairmarket.resaleservice.service;

import com.fairmarket.resaleservice.dto.ResaleQueueRequestDTO;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.stereotype.Service;

@Service
public class RabbitAdminService {

    private final RabbitAdmin rabbitAdmin;

    public RabbitAdminService(RabbitAdmin rabbitAdmin) {
        this.rabbitAdmin = rabbitAdmin;
    }

    public void createResaleQueue(ResaleQueueRequestDTO dto) {
        Queue queue = new Queue(dto.getQueueName(), dto.isDurable());
        rabbitAdmin.declareQueue(queue);
    }

    public boolean deleteQueue(String queueName) {
        return rabbitAdmin.deleteQueue(queueName);
    }
}
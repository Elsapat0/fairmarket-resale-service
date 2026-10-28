package com.fairmarket.resaleservice.consumer;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ResaleQueueListener {

    @RabbitListener(queues = "${fairmarket.resale.queue}")
    public void processResaleTicket(String resaleMessage, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        try {
            if (resaleMessage.contains("EXPIRED")) {
                throw new IllegalStateException("Tiempo de asignación de compra expirado");
            }

            System.out.println("Entrada en reventa procesada correctamente: " + resaleMessage);
            channel.basicAck(tag, false);

        } catch (Exception e) {
            System.err.println("Fallo en la reventa (" + e.getMessage() + "). Enviando a DLQ...");
            channel.basicNack(tag, false, false);
        }
    }
}
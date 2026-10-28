package com.fairmarket.resaleservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfig {

    @Value("${fairmarket.resale.queue}")
    private String queueName;

    @Value("${fairmarket.resale.dlq}")
    private String dlqName;

    @Value("${fairmarket.resale.exchange}")
    private String exchangeName;

    @Value("${fairmarket.resale.dlx}")
    private String dlxName;

    @Value("${fairmarket.resale.routing-key}")
    private String routingKey;

    @Value("${fairmarket.resale.dlq-routing-key}")
    private String dlqRoutingKey;

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public DirectExchange resaleExchange() {
        return new DirectExchange(exchangeName);
    }

    @Bean
    public DirectExchange resaleDeadLetterExchange() {
        return new DirectExchange(dlxName);
    }

    @Bean
    public Queue resaleQueue() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", dlxName);
        args.put("x-dead-letter-routing-key", dlqRoutingKey);
        return QueueBuilder.durable(queueName).withArguments(args).build();
    }

    @Bean
    public Queue resaleDeadLetterQueue() {
        return QueueBuilder.durable(dlqName).build();
    }

    @Bean
    public Binding resaleBinding() {
        return BindingBuilder.bind(resaleQueue()).to(resaleExchange()).with(routingKey);
    }

    @Bean
    public Binding resaleDlqBinding() {
        return BindingBuilder.bind(resaleDeadLetterQueue()).to(resaleDeadLetterExchange()).with(dlqRoutingKey);
    }
}
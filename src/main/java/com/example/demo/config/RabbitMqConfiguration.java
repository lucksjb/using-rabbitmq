package com.example.demo.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMqConfiguration  { 
    public static final String EXCHANGE_NAME = "Machines";
    

    @Bean
    public Exchange declareExchange() {
        return ExchangeBuilder.directExchange(EXCHANGE_NAME)
                .durable(true)
                .build();
    }

    @Bean
    public Queue queueUM() {
        return QueueBuilder.durable("fila-01")
                .build();
    }

    
    @Bean
    public Queue queueDOIS() {
        return QueueBuilder
                .durable("fila-02")
                .build();
    }

    
    @Bean
    public Queue queueTRES() {
        return QueueBuilder.durable("fila-03")
                .build();
    }

    @Bean
    public Binding bindingMachineQueueUM(Exchange exchange, Queue queueUM) {
        return BindingBuilder.bind(queueUM)
                .to(exchange)
                .with("Machines.fila-01")
                .noargs();
    }
    @Bean

    public Binding bindingMachineQueueDOIS(Exchange exchange, Queue queueDOIS) {
        return BindingBuilder.bind(queueDOIS)
                .to(exchange)
                .with("Machines.fila-02")
                .noargs();
    }
    @Bean
    public Binding bindingMachineQueueTRES(Exchange exchange, Queue queueTRES) {
        return BindingBuilder.bind(queueTRES)
                .to(exchange)
                .with("Machines.fila-03")
                .noargs();
    }

    // bean de conversão do objetos java / json
    @Bean
    @Autowired
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final var rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public MessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
package com.example.demo.services;

import java.time.LocalDate;

import com.example.demo.config.RabbitMqConfiguration;
import com.example.demo.models.Cliente;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SendMessage {
    @Autowired
    private RabbitTemplate rabbitTemplate; 
    
    public void sendMessage() {
        Cliente cli = Cliente
                        .Builder
                        .newCliente()
                        .addId(1L)
                        .addNome("luciano DS")
                        .addNascimento(LocalDate.of(1971, 11, 17))
                        .build();
        
        rabbitTemplate.convertAndSend(RabbitMqConfiguration.EXCHANGE_NAME, "Machines.fila-03", cli);

    }
}

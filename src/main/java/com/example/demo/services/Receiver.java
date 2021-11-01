package com.example.demo.services;

import java.io.IOException;

import com.example.demo.models.Cliente;
import com.rabbitmq.client.Channel;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Receiver {


    @RabbitListener(queues = { "fila-01" })
    public void receiveMessageFromFila01(Cliente message) {
        System.out.println("Received fila-01 message: " + message);

    }

    @RabbitListener(queues = { "fila-02" })
    public void receiveMessageFromFila02(Cliente message) {
        System.out.println("Received fila-02 message: " + message);
        

    }

    @RabbitListener(queues = { "fila-03" }, ackMode = "MANUAL" )
    public void receiveMessageFromFila03(Cliente message, Channel channel) throws IOException {
        System.out.println("Received fila-03 message: " + message);
        System.out.println("Processando mensagem ...");
        System.out.println("Processando mensagem ...");
        System.out.println("Processando mensagem ...");
        System.out.println("Processando mensagem ...");
        System.out.println("Processando mensagem ...");
        System.out.println("Processando mensagem ...");
        
        System.out.println("Excluindo da fila : channel.basicAck(0L, true)");
         channel.basicAck(0L, true);  
         
    }

}
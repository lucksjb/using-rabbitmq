<center><img src="./images/rabbitmq_logo.png"></center>  
---
# PROJETO EXEMPLO DE RABBITMQ

## Objetivo
    O projeto tem como objetivo explicar e exemplificar a integração entre microserviços via RabbitMQ.

### Conceitos   
**Fazendo uma analogia com o sitemas de correios:**   
    Imagine o sistema de mensageria como se fosse um sistema de correios, onde, quando você quer enviar uma carta a alguém, você nao envia direto a pessoa, e sim leva a um agência de correio. Na agência de correio a casa de cada pessoa faz parte de uma rota, onde o destino final é entregar a carta na casa da pessoa.
    No liguagem de RabbitMQ, você é o **Producer/Publisher** (ou produtor da mensagem), o destinatário é o **Consumer** (ou consumidor da mensagem) e a agência de correio uma **Exchange**.
    As caixinhas de correio são as ***Queues** as rotas que pertencem a agência/casa do destinatário são as **Routes**




[RabbitMqConfiguration](./src/main/java/com/example/demo/config/RabbitMqConfiguration.java) - Utilizado para configurar a Exchange, as filas e os Bindings, além da criação do Bean **RabbitTemplate** utilizado para o envio e conversão das mensagens.
Essa configuração é feita toda com beans. 

Devido ao rabbit não conseguir serializar/deserealizar os objetos que contem LocalDate/LocalDateTime foram criados alguns conversores Jackson. São eles:   
**LocalDate:**   
[LocalDateToStringConverter](./src/main/java/com/example/demo/config/jackson/LocalDateToStringConverter.java)   
[StringToLocalDateConverter](./src/main/java/com/example/demo/config/jackson/StringToLocalDateConverter.java)   
**LocalDateTime:**   
[LocalDateTimeToStringConverter](./src/main/java/com/example/demo/config/jackson/LocalDateTimeToStringConverter.java)      
[StringToLocalDateTimeConverter](./src/main/java/com/example/demo/config/jackson/StringToLocalDateTimeConverter.java)       

Para que isso funcione perfeitamente, as propriedades LocalDate/LocalDateTime devem ser anotadas com o **@JsonSerialize / @JsonDeserialize**.
```
// EXEMPLO
public class Cliente {
    private Long id;
    private String nome;

    @JsonSerialize(converter = LocalDateToStringConverter.class)
    @JsonDeserialize(converter = StringToLocalDateConverter.class)
    private LocalDate nascimento;

    .
    .
    .
    .
```

Para o envio de mensagens utilizaremos entao a injeção do bean **RabbitTemplate** conforme o exemplo em [SendMessage](./src/main/java/com/example/demo/services/SendMessage.java). 

***IMPORTANTE:***    
O nome da routing-key, deverá sempre ser composto pelo **nome-da-exchange.nome-da-fila**   
```
 rabbitTemplate.convertAndSend(RabbitMqConfiguration.EXCHANGE_NAME, "Machines.fila-03", cli);
````


***RECEIVERS***
O spring facilita em muito a criação dos Receivers através da anotação **@RabbitListener** que deve ser colocado sobre o método que irá receber a mensagem.
```
    // o **@RabbitListener** pode receber uma lista de filas, 
    // dessa forma, tudo que for para uma dessas filas será processado por este método
    @RabbitListener(queues = { "fila-01" })
    public void receiveMessageFromFila01(Cliente message) {
        System.out.println("Received fila-01 message: " + message);
        
    }
``` 
***IMPORTANTE***  se não for especificado o ackMode="MANUAL", uma vez consumida a mensagem a mesma será excluída da fila.

```
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
         channel.basicAck(0L, true);  // será excluida da fila somente após este comando.
         
    }
```

No arquivo [Application.properties](./src/main/resources/application.properties), devemos setar como a aplicação deve localizar o RabbitMQ.   
Isso é feito através das seguntes chaves:    

```
#Rabbit MQ
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=admin
spring.rabbitmq.password=admin
```

Para ter uma instancia do RabbitMQ server rodando utilizamo o [docker-compose](./docker-compose.yml)    
a porta ulizada para troca de mensagens é a porta 5672
a porta utilizada para acessar o gerenciador do RabbitMQ é a 15672
esse acesso é feito através do navegador no endereço http://localhost:15672/#/

Para levantar o container _docker-compose up -d_


***Links Interessantes***   
https://www.rabbitmq.com/   
https://www.rabbitmq.com/tutorials/amqp-concepts.html   
https://spring.io/guides/gs/messaging-rabbitmq/   
https://medium.com/dev-cave/rabbit-mq-parte-i-c15e5f89d94   
http://nelsonsar.github.io/2013/10/29/AMQP-building-blocks.html   
http://nelsonsar.github.io/2013/11/07/RabbitMQ-exchange-types.html   
https://medium.com/totvsdevelopers/spring-boot-rabbitmq-porque-considerar-o-uso-de-mensageria-no-seu-projeto-3aed6637c4b4   


by Luck 
        12:21 01/11/2021

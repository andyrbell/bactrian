package com.bactrian;


import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;

public class RabbitRoute {//extends RouteBuilder {


//    @Override
//    public void configure() throws Exception {
//        from("timer:hello?period=1000")
//                .process(exchange -> {
//                    Message msg = exchange.getIn();
//                    String body = msg.getBody(String.class);
//
//                    msg.setBody(exchange.getExchangeId());
//                })
//                .to("rabbitmq://localhost:5671/myExchange?connectionFactory=#myConnectionFactory&skipQueueDeclare=true");
//
//        from("rabbitmq://localhost:5671/myExchange?connectionFactory=#myConnectionFactory&queue=myQueue")
//                .to("log:out");
//    }
}

package com.smile.config;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;

/**
 * @author smilePlus
 * @description: Direct模式
 * @date 2021/3/28 11:21
 */
// @Configuration
public class RabbitMQDirectConfig {
    public static final String QUEUE01 = "queue_direct01";
    public static final String QUEUE02 = "queue_direct02";
    public static final String EXCHANGE = "directExchange";
    public static final String ROUTING_KEY01 = "queue.red";
    public static final String ROUTING_KEY02 = "queue.green";


    @Bean
    public Queue queue01() {
        return new Queue(QUEUE01);
    }

    @Bean
    public Queue queue02() {
        return new Queue(QUEUE02);
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(EXCHANGE);
    }

    // @Bean
    // public Binding binding01() {
    //     return BindingBuilder.bind(queue01()).to(directExchange()).with(ROUTING_KEY01);
    // }

    // @Bean
    // public Binding binding02() {
    //     return BindingBuilder.bind(queue02()).to(directExchange()).with(ROUTING_KEY02);
    // }
}

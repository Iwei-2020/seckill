package com.smile.config;

/**
 * @author smilePlus
 * @description: RabbitMQ配置类
 * @date 2021/3/27 21:54
 */
// @Configuration
public class RabbitMQFanoutConfig {

    public static final String QUEUE01 = "queue_fanout01";
    public static final String QUEUE02 = "queue_fanout02";
    public static final String EXCHANGE = "fanoutExchange";
    //
    // @Bean
    // public Queue queue() {
    //     return new Queue("queue", true);
    // }
    // @Bean
    // public Queue queue01() {
    //     return new Queue(QUEUE01);
    // }
    // @Bean
    // public Queue queue02() {
    //     return new Queue(QUEUE02);
    // }
    //
    // @Bean
    // public FanoutExchange fanoutExchange() {
    //     return new FanoutExchange(EXCHANGE);
    // }

    // @Bean
    // public Binding binding01() {
    //     return BindingBuilder.bind(queue01()).to(fanoutExchange());
    // }
    //
    // @Bean
    // public Binding binding02() {
    //     return BindingBuilder.bind(queue02()).to(fanoutExchange());
    // }
}

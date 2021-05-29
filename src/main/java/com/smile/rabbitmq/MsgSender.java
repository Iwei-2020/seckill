package com.smile.rabbitmq;

import com.smile.config.RabbitMQDirectConfig;
import com.smile.config.RabbitMQFanoutConfig;
import com.smile.config.RabbitMQTopicConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author smilePlus
 * @description: 生产者
 * @date 2021/3/28 10:20
 */
@Service
@Slf4j
public class MsgSender {
    private RabbitTemplate rabbitTemplate;

    public void sendMsgByFanout(Object msg) {
        log.info("发送消息{}", msg);
        rabbitTemplate.convertAndSend(RabbitMQFanoutConfig.EXCHANGE, null, msg);
    }

    public void sendMsgByDirect(Object msg, String routingKey) {
        log.info("发送RoutingKey为{}，消息为{}的消息", routingKey, msg);
        rabbitTemplate.convertAndSend(RabbitMQDirectConfig.EXCHANGE, routingKey, msg);
    }

    public void sendMsgByTopic(Object msg, String routingKey) {
        log.info("发送RoutingKey为{}，消息为{}的消息", routingKey, msg);
        rabbitTemplate.convertAndSend(RabbitMQTopicConfig.EXCHANGE, routingKey, msg);
    }

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
}

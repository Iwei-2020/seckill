package com.smile.rabbitmq;

import com.smile.config.RabbitMQDirectConfig;
import com.smile.config.RabbitMQFanoutConfig;
import com.smile.config.RabbitMQTopicConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * @author smilePlus
 * @description: 消费者
 * @date 2021/3/28 10:23
 */
@Service
@Slf4j
public class MsgReceiver {

    @RabbitListener(queues = "queue")
    public void receive(Object msg) {
        log.info("接收消息{}", msg);
    }

    @RabbitListener(queues = RabbitMQFanoutConfig.QUEUE01)
    public void receiveByFanout01(Object msg) {
        log.info("{}接收消息{}", RabbitMQFanoutConfig.QUEUE01, msg);
    }

    @RabbitListener(queues = RabbitMQFanoutConfig.QUEUE02)
    public void receiveByFanout02(Object msg) {
        log.info("{}接收消息{}", RabbitMQFanoutConfig.QUEUE02, msg);
    }

    @RabbitListener(queues = RabbitMQDirectConfig.QUEUE01)
    public void receiveByDirect01(Object msg) {
        log.info("{}接收消息{}", RabbitMQDirectConfig.QUEUE01, msg);
    }

    @RabbitListener(queues = RabbitMQDirectConfig.QUEUE02)
    public void receiveByDirect02(Object msg) {
        log.info("{}接收消息{}", RabbitMQDirectConfig.QUEUE02, msg);
    }

    @RabbitListener(queues = RabbitMQTopicConfig.QUEUE01)
    public void receiveByTopic01(Object msg) {
        log.info("{}接收消息{}", RabbitMQTopicConfig.QUEUE01, msg);
    }

    @RabbitListener(queues = RabbitMQTopicConfig.QUEUE02)
    public void receiveByTopic02(Object msg) {
        log.info("{}接收消息{}", RabbitMQTopicConfig.QUEUE02, msg);
    }
}

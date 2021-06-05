package com.github.wenhao.listener;

import com.github.wenhao.domain.OrderCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import static com.github.wenhao.domain.MessageQueue.EXCHANGE_ORDER;
import static com.github.wenhao.domain.MessageQueue.QUEUE_ORDER_CREATED;
import static com.github.wenhao.domain.MessageQueue.ROUTING_ORDER_CREATED;

@Slf4j
@Service
@ConditionalOnProperty(prefix = "application.mq", name = "enabled", havingValue = "true")
@RabbitListener(bindings = {@QueueBinding(value = @Queue(QUEUE_ORDER_CREATED), exchange = @Exchange(value = EXCHANGE_ORDER, type = ExchangeTypes.TOPIC), key = ROUTING_ORDER_CREATED)})
public class OrderEventListener {

    @RabbitHandler
    public void onOrderCreated(OrderCreatedEvent event) {
        log.info("order: {}, date: {}", event.getOrderId(), event.getDate());
    }
}

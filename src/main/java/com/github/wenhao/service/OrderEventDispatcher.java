package com.github.wenhao.service;

import com.github.wenhao.domain.OrderEvent;
import com.github.wenhao.event.annotation.Event;
import com.github.wenhao.event.dispatcher.EventDispatcher;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(prefix = "application.mq", name = "enabled", havingValue = "true")
public class OrderEventDispatcher implements EventDispatcher<OrderEvent> {

    private final AmqpTemplate amqpTemplate;

    public OrderEventDispatcher(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    @Override
    public void dispatch(OrderEvent event) {
        Event annotation = AnnotationUtils.findAnnotation(event.getClass(), Event.class);
        amqpTemplate.convertAndSend(annotation.exchange(), annotation.routingKey(), event);
    }
}

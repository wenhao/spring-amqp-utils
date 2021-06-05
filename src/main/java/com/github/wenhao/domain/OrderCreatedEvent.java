package com.github.wenhao.domain;


import com.github.wenhao.event.annotation.Event;

import java.util.Date;

import static com.github.wenhao.domain.MessageQueue.EXCHANGE_ORDER;
import static com.github.wenhao.domain.MessageQueue.ROUTING_ORDER_CREATED;

@Event(exchange = EXCHANGE_ORDER, routingKey = ROUTING_ORDER_CREATED)
public class OrderCreatedEvent extends OrderEvent {

    private Date date;

    public OrderCreatedEvent() {
    }

    public OrderCreatedEvent(int orderId, Date date) {
        super(orderId);
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

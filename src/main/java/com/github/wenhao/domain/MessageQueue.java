package com.github.wenhao.domain;

public interface MessageQueue {
    String EXCHANGE_ORDER = "exchange.order";
    String ROUTING_ORDER_CREATED = "routing.order.created";

    String QUEUE_ORDER_CREATED = "queue.order.created";
}

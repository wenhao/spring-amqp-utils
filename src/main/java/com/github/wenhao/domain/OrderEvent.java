package com.github.wenhao.domain;

public abstract class OrderEvent {

    private int orderId;

    public OrderEvent() {
    }

    public OrderEvent(int orderId) {
        this.orderId = orderId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}

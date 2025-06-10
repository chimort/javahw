package com.events;

import java.math.BigDecimal;

public class OrderCreatedEvent {
    private Long orderId;
    private String userId;
    private BigDecimal amount;

    public OrderCreatedEvent() {}
    public OrderCreatedEvent(Long orderId, String userId, BigDecimal amount) {
        this.orderId = orderId;
        this.userId = userId;
        this.amount = amount;
    }

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
}
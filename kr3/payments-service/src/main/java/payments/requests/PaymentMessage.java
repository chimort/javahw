package payments.requests;

import java.math.BigDecimal;


public class PaymentMessage {

    private Long orderId;
    private String userId;
    private BigDecimal amount;

    public PaymentMessage() {
    }

    public Long getOrderId() {
        return orderId;
    }
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}

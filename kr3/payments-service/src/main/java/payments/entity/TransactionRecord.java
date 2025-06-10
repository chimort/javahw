package payments.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "transaction_records")
public class TransactionRecord {

    @Id
    @Column(name = "order_id", nullable = false)
    private Long orderId;

    @Column(name = "status", nullable = false)
    private String status;

    public TransactionRecord() {
    }

    public TransactionRecord(Long orderId, String status) {
        this.orderId = orderId;
        this.status = status;
    }

    public Long getOrderId() {
        return orderId;
    }

    public String getStatus() {
        return status;
    }
}

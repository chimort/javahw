package payments.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;


@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "balance", nullable = false, scale = 2, precision = 19)
    private BigDecimal balance;

    public Account() {
    }

    public Account(String userId, BigDecimal balance) {
        this.userId = userId;
        this.balance = balance;
    }

    public String getUserId() {
        return userId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}

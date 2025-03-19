package Entities;

import java.time.LocalDate;

import Visitor.ExportVisitor;

public class Operation {
    public enum OperationType {
        INCOME,
        CONSUMPTION
    }

    private final int id_;
    private final OperationType type_;
    private final BankAccount back_account_id_;
    private final double amount_;
    private final LocalDate date_;
    private final Category category_id_;


    public Operation(int id, OperationType type, BankAccount back_account_id, double amount, LocalDate date, Category category_id) {
        if (amount < 0) {
            throw new IllegalArgumentException("Сумма операции не может быть отрицательной");
        }
        this.id_ = id;
        this.type_ = type;
        this.back_account_id_ = back_account_id;
        this.amount_ = amount;
        this.date_ = date;
        this.category_id_ = category_id;
    }

    public int getId() { return id_; }
    public OperationType getType() { return type_; }
    public BankAccount getBankAccount() { return back_account_id_; }
    public double getAmount() { return amount_; }
    public LocalDate getDate() { return date_; }
    public Category getCategory() { return category_id_; }

    public void accept(ExportVisitor visitor) {
        visitor.visit(this);
    }
}

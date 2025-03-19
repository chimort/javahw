package Entities;

import Visitor.ExportVisitor;

public class BankAccount {
    private final int id_;
    private String name_;
    private double balance_;

    public BankAccount(int id, String name, double balance) {
        this.id_ = id;
        this.name_ = name;
        this.balance_ = balance;
    }

    public int getId() { return id_; }
    public String getName() { return name_; }

    public double getBalance() { return balance_; }

    public void setName(String name) { this.name_ = name; }

    public void deposit(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Сумма депозита не может быть отрицательной");
        }
        balance_ += amount;
    }

    public void withdraw(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Сумма снятия не может быть отрицательной");
        }
        if (balance_ < amount) {
            throw new IllegalArgumentException("Недостаточно средств на счёте");
        }
        balance_ -= amount;
    }

    public void accept(ExportVisitor visitor) {
        visitor.visit(this);
    }
}

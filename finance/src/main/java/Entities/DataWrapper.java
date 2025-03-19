package Entities;

import java.util.List;

public class DataWrapper {
    private List<BankAccount> bankAccounts;
    private List<Category> categories;
    private List<Operation> operations;

    public DataWrapper() {}

    public DataWrapper(List<BankAccount> bankAccounts, List<Category> categories, List<Operation> operations) {
        this.bankAccounts = bankAccounts;
        this.categories = categories;
        this.operations = operations;
    }

    public List<BankAccount> getBankAccounts() {
        return bankAccounts;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public void setBankAccounts(List<BankAccount> bankAccounts) { this.bankAccounts = bankAccounts; }
    public void setCategories(List<Category> categories) { this.categories = categories; }
    public void setOperations(List<Operation> operations) { this.operations = operations; }
}

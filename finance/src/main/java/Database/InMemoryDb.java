package Database;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import Entities.BankAccount;
import Entities.Category;
import Entities.Operation;

public class InMemoryDb implements DbProxy {
    private List<BankAccount> bank_accounts_ = new ArrayList<>();
    private List<Category> categories_ = new ArrayList<>();
    private List<Operation> operations_ = new ArrayList<>();

    @Override
    public void addBankAccount(BankAccount account) {
        bank_accounts_.add(account);
    }

    @Override
    public void addCategory(Category category) {
        categories_.add(category);
    }

    @Override
    public void addOperation(Operation operation) {
        operations_.add(operation);
    }

    @Override
    public List<BankAccount> getBankAccounts() {
        return bank_accounts_;
    }

    @Override
    public List<Category> getCategories() {
        return categories_;
    }

    @Override
    public List<Operation> getOperations() {
        return operations_;
    }

    @Override
    public void removeBankAccount(int id) {
        bank_accounts_.removeIf(acc -> acc.getId() == id);
    }

    @Override
    public void removeCategory(int id) {
        categories_.removeIf(cat -> cat.getId() == id);
    }

    @Override
    public void removeOperation(int id) {
        operations_.removeIf(op -> op.getId() == id);
    }

    @Override
    public BankAccount getBankAccountById(int id) {
        return bank_accounts_.stream().filter(acc -> acc.getId() == id).findFirst().orElse(null);
    }

    @Override
    public Category getCategoryById(int id) {
        return categories_.stream().filter(cat -> cat.getId() == id).findFirst().orElse(null);
    }

    @Override
    public Operation getOperationById(int id) {
        return operations_.stream().filter(op -> op.getId() == id).findFirst().orElse(null);
    }
}

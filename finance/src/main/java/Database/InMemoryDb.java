package Database;

import java.util.List;
import java.util.ArrayList;

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
}

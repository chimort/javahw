package Repository;

import Entities.BankAccount;
import Entities.Category;
import Entities.Operation;

import java.util.List;

public interface Repository {
    void addBankAccount(BankAccount account);
    void addCategory(Category category);
    void addOperation(Operation operation);

    List<BankAccount> getBankAccounts();
    List<Category> getCategories();
    List<Operation> getOperations();
}
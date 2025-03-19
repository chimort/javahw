package Database;

import Entities.BankAccount;
import Entities.Category;
import Entities.Operation;

import java.util.List;

public interface DbProxy {
    void addBankAccount(BankAccount account);
    void addCategory(Category category);
    void addOperation(Operation operation);

    List<BankAccount> getBankAccounts();
    List<Operation> getOperations();
    List<Category> getCategories();
}

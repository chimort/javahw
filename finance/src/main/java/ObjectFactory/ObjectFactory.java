package ObjectFactory;

import Entities.BankAccount;
import Entities.Category;
import Entities.Operation;

import java.time.LocalDate;

public class ObjectFactory {
    public static BankAccount createBankAccount(int id, String name, double balance) {
        return new BankAccount(id, name, balance);
    }

    public static Category createCategory(int id, Category.CategoryType type, String name) {
        return new Category(id, type, name);
    }

    public static Operation createOperation(int id, Operation.OperationType type, BankAccount back_account_id,
                                            double amount, LocalDate date, Category category_id) {
        return new Operation(id, type, back_account_id, amount, date, category_id);
    }
}

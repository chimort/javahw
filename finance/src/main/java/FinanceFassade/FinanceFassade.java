package FinanceFassade;

import Entities.BankAccount;
import Database.DbProxy;
import ObjectFactory.ObjectFactory;
import Entities.Operation;
import Entities.Category;

import java.time.LocalDate;
import java.util.List;

public class FinanceFassade {
    private DbProxy proxy_;

    public FinanceFassade(DbProxy proxy) {
        this.proxy_ = proxy;
    }

    private boolean isUniqueBankId(int id) {
        return proxy_.getBankAccounts().stream().anyMatch(account -> account.getId() == id);
    }

    private boolean isUniqueCategoryId(int id) {
        return proxy_.getCategories().stream().anyMatch(cat -> cat.getId() == id);
    }

    private boolean isUniqueOperationId(int id) {
        return proxy_.getOperations().stream().anyMatch(operation -> operation.getId() == id);
    }

    public BankAccount createBankAccount(int id, String name, double balance) {
        if (isUniqueBankId(id)) {
            throw new IllegalArgumentException("Счёт с id " + id + " уже существует");
        }
        if (balance < 0) {
            throw new IllegalArgumentException("Баланс не может быть отрицательным");
        }
        BankAccount account = ObjectFactory.createBankAccount(id, name, balance);
        proxy_.addBankAccount(account);
        return account;
    }

    public Category createCategory(int id, Category.CategoryType type, String name) {
        if (isUniqueCategoryId(id)) {
            throw new IllegalArgumentException("Категория с id " + id + " уже существует");
        }
        Category category = ObjectFactory.createCategory(id, type, name);
        proxy_.addCategory(category);
        return category;
    }

    public Operation createOperation(int id, Operation.OperationType type, int bank_account_id,
                                     double amount, LocalDate date, int categoryId) {
        if (isUniqueOperationId(id)) {
            throw new IllegalArgumentException("Операция с id " + id + " уже существует");
        }

        BankAccount bank_account = proxy_.getBankAccounts().stream()
                .filter(acc -> acc.getId() == bank_account_id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Счет с id" + bank_account_id + " не найден"));

        Category category = proxy_.getCategories().stream()
                .filter(cat -> cat.getId() == categoryId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Категория с id " + categoryId + " не найдена"));


        if (type == Operation.OperationType.CONSUMPTION && bank_account.getBalance() < amount) {
            throw new IllegalArgumentException("Недостаточно средств на счете для операции");
        }

        if (type == Operation.OperationType.INCOME) {
            bank_account.deposit(amount);
        } else {
            bank_account.withdraw(amount);
        }

        Operation oper = ObjectFactory.createOperation(id, type, bank_account, amount, date, category);
        proxy_.addOperation(oper);
        return oper;
    }

    public void updateBankAccount(int id, String new_name, double new_balance) {
        BankAccount acc = proxy_.getBankAccountById(id);
        if (acc != null) {
            acc.setName(new_name);
            acc.setBalance(new_balance);
        }
    }

    public void updateCategory(int id, String new_name, Category.CategoryType new_type) {
        Category cat = proxy_.getCategoryById(id);
        if (cat != null) {
            cat.setName(new_name);
            cat.setCategoryType(new_type);
        }
    }

    public void updateOperation(int id, Operation.OperationType new_type, double new_amount,
                                LocalDate new_date, int newAccId, int newCatId) {
        Operation old_op = proxy_.getOperationById(id);
        if (old_op != null) {
            proxy_.removeOperation(id);

            BankAccount new_acc = proxy_.getBankAccountById(newAccId);
            Category new_cat = proxy_.getCategoryById(newCatId);

            Operation new_op = new Operation(id, new_type, new_acc, new_amount, new_date, new_cat);
            proxy_.addOperation(new_op);
        }
    }


    public void deleteBankAccount(int id) {
        proxy_.removeBankAccount(id);
    }

    public void deleteCategory(int id) {
        proxy_.removeCategory(id);
    }

    public void deleteOperation(int id) {
        proxy_.removeOperation(id);
    }


    public List<BankAccount> getAllBankAccounts() {
        return proxy_.getBankAccounts();
    }

    public List<Category> getAllCategories() {
        return proxy_.getCategories();
    }

    public List<Operation> getAllOperations() {
        return proxy_.getOperations();
    }

}

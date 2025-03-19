package Visitor;

import Entities.BankAccount;
import Entities.Category;
import Entities.Operation;

public interface Visitor {
    void visit(BankAccount account);
    void visit(Category category);
    void visit(Operation operation);
}

package Tests;

import Database.DbProxy;
import Database.InMemoryDb;
import Entities.BankAccount;
import Entities.Category;
import Entities.Operation;
import Entities.Category.CategoryType;
import Entities.Operation.OperationType;
import FinanceFassade.FinanceFassade;
import Commands.CreateBankAccountCommand;
import Commands.CreateCategoryCommand;
import Commands.CreateOperationCommand;
import Commands.Command;
import Commands.CommandDecorator;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FinanceCommandTest {
    private FinanceFassade fassade;

    @BeforeEach
    void setup() {
        DbProxy dbProxy = new InMemoryDb();
        fassade = new FinanceFassade(dbProxy);
    }

    @Test
    @DisplayName("Создание банковского счета")
    void testCreateBankAccount() {
        Command createCmd = new CreateBankAccountCommand(fassade, 1, "Test Account", 5000.0);
        new CommandDecorator(createCmd).execute();

        List<BankAccount> accounts = fassade.getAllBankAccounts();
        assertEquals(1, accounts.size(), "Ожидалось 1 счет");
        assertEquals("Test Account", accounts.get(0).getName());
        assertEquals(5000.0, accounts.get(0).getBalance(), 0.01);
    }

    @Test
    @DisplayName("Создание категории")
    void testCreateCategory() {
        Command createCmd = new CreateCategoryCommand(fassade, 1, CategoryType.INCOME, "Salary");
        new CommandDecorator(createCmd).execute();

        List<Category> categories = fassade.getAllCategories();
        assertEquals(1, categories.size(), "Ожидалась 1 категория");
        assertEquals("Salary", categories.get(0).getName());
        assertEquals(CategoryType.INCOME, categories.get(0).getCategoryType());
    }

    @Test
    @DisplayName("Создание операции")
    void testCreateOperation() {
        fassade.createBankAccount(1, "Main Account", 10000.0);
        fassade.createCategory(1, CategoryType.INCOME, "Job");

        Command createCmd = new CreateOperationCommand(fassade, 1, OperationType.INCOME, 1, 2000.0, LocalDate.of(2024, 3, 20), 1);
        new CommandDecorator(createCmd).execute();

        List<Operation> operations = fassade.getAllOperations();
        assertEquals(1, operations.size(), "Ожидалась 1 операция");
        assertEquals(OperationType.INCOME, operations.get(0).getType());
        assertEquals(2000.0, operations.get(0).getAmount(), 0.01);
        assertEquals("Job", operations.get(0).getCategory().getName());
    }

    @Test
    @DisplayName("Обновление банковского счета")
    void testUpdateBankAccount() {
        fassade.createBankAccount(2, "Old Account", 3000.0);
        fassade.updateBankAccount(2, "Updated Account", 5000.0);

        BankAccount updated = fassade.getAllBankAccounts().stream()
                .filter(acc -> acc.getId() == 2)
                .findFirst()
                .orElse(null);

        assertNotNull(updated, "Счет должен существовать");
        assertEquals("Updated Account", updated.getName());
        assertEquals(5000.0, updated.getBalance(), 0.01);
    }

    @Test
    @DisplayName("Обновление категории")
    void testUpdateCategory() {
        fassade.createCategory(2, CategoryType.CONSUMPTION, "Food");
        fassade.updateCategory(2, "Groceries", CategoryType.CONSUMPTION);

        Category updated = fassade.getAllCategories().stream()
                .filter(cat -> cat.getId() == 2)
                .findFirst()
                .orElse(null);

        assertNotNull(updated, "Категория должна существовать");
        assertEquals("Groceries", updated.getName(), "Название категории не обновилось");
        assertEquals(CategoryType.CONSUMPTION, updated.getCategoryType(), "Тип категории не обновился");
    }


    @Test
    @DisplayName("Обновление операции")
    void testUpdateOperation() {
        fassade.createBankAccount(3, "Salary Account", 10000.0);
        fassade.createCategory(3, CategoryType.INCOME, "Freelance");
        fassade.createOperation(3, OperationType.INCOME, 3, 2500.0, LocalDate.of(2024, 3, 21), 3);

        fassade.updateOperation(3, OperationType.CONSUMPTION, 1000.0, LocalDate.of(2024, 3, 22), 3, 3);

        Operation updated = fassade.getAllOperations().stream()
                .filter(op -> op.getId() == 3)
                .findFirst()
                .orElse(null);

        assertNotNull(updated, "Операция должна существовать");
        assertEquals(OperationType.CONSUMPTION, updated.getType());
        assertEquals(1000.0, updated.getAmount(), 0.01);
    }

    @Test
    @DisplayName("Удаление счета")
    void testDeleteBankAccount() {
        fassade.createBankAccount(4, "To Be Deleted", 2000.0);
        fassade.deleteBankAccount(4);

        BankAccount deleted = fassade.getAllBankAccounts().stream()
                .filter(acc -> acc.getId() == 4)
                .findFirst()
                .orElse(null);

        assertNull(deleted, "Счет должен быть удален");
    }

    @Test
    @DisplayName("Удаление категории")
    void testDeleteCategory() {
        fassade.createCategory(4, CategoryType.CONSUMPTION, "Rent");
        fassade.deleteCategory(4);

        Category deleted = fassade.getAllCategories().stream()
                .filter(cat -> cat.getId() == 4)
                .findFirst()
                .orElse(null);

        assertNull(deleted, "Категория должна быть удалена");
    }

    @Test
    @DisplayName("Удаление операции")
    void testDeleteOperation() {
        fassade.createBankAccount(5, "Expenses Account", 5000.0);
        fassade.createCategory(5, CategoryType.CONSUMPTION, "Bills");
        fassade.createOperation(5, OperationType.CONSUMPTION, 5, 1500.0, LocalDate.of(2024, 3, 23), 5);

        fassade.deleteOperation(5);

        Operation deleted = fassade.getAllOperations().stream()
                .filter(op -> op.getId() == 5)
                .findFirst()
                .orElse(null);

        assertNull(deleted, "Операция должна быть удалена");
    }
}

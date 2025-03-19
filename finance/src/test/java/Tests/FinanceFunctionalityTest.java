package Tests;

import Entities.BankAccount;
import Entities.Category;
import Entities.Operation;
import Entities.Category.CategoryType;
import Entities.Operation.OperationType;
import FinanceFassade.FinanceFassade;
import Database.DbProxy;
import Database.InMemoryDb;  // Предполагается, что InMemoryDb – конкретная реализация DbProxy
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FinanceFunctionalityTest {

    private FinanceFassade fassade;

    @BeforeEach
    void setup() {
        DbProxy dbProxy = new InMemoryDb();
        fassade = new FinanceFassade(dbProxy);
    }

    @Test
    @DisplayName("Проверка создания банковских счетов")
    void testCreateBankAccounts() {
        fassade.createBankAccount(1, "Primary Account", 1000.0);
        fassade.createBankAccount(2, "Savings Account", 5000.0);

        List<BankAccount> accounts = fassade.getAllBankAccounts();
        assertEquals(2, accounts.size(), "Количество счетов должно быть равно 2");

        BankAccount account1 = accounts.stream().filter(acc -> acc.getId() == 1).findFirst().orElse(null);
        assertNotNull(account1, "Счёт с ID 1 должен существовать");
        assertEquals("Primary Account", account1.getName());
        assertEquals(1000.0, account1.getBalance(), 0.001);
    }

    @Test
    @DisplayName("Проверка создания категорий")
    void testCreateCategories() {
        fassade.createCategory(1, CategoryType.INCOME, "Salary");
        fassade.createCategory(2, CategoryType.CONSUMPTION, "Food");

        List<Category> categories = fassade.getAllCategories();
        assertEquals(2, categories.size(), "Количество категорий должно быть равно 2");

        Category cat1 = categories.stream().filter(cat -> cat.getId() == 1).findFirst().orElse(null);
        assertNotNull(cat1, "Категория с ID 1 должна существовать");
        assertEquals("Salary", cat1.getName());
        assertEquals(CategoryType.INCOME, cat1.getCategoryType());
    }

    @Test
    @DisplayName("Проверка создания операций и изменения баланса")
    void testCreateOperations() {
        fassade.createBankAccount(10, "Test Account", 1000.0);
        fassade.createCategory(10, CategoryType.INCOME, "Bonus");
        fassade.createCategory(11, CategoryType.CONSUMPTION, "Shopping");

        fassade.createOperation(100, OperationType.INCOME, 10, 500.0, LocalDate.of(2025, 3, 19), 10);
        fassade.createOperation(101, OperationType.CONSUMPTION, 10, 200.0, LocalDate.of(2025, 3, 20), 11);

        BankAccount account = fassade.getAllBankAccounts().stream().filter(acc -> acc.getId() == 10).findFirst().orElse(null);
        assertNotNull(account, "Счёт с ID 10 должен существовать");

        assertEquals(1300.0, account.getBalance(), 0.001, "Баланс счета должен быть 1300.0");

        List<Operation> operations = fassade.getAllOperations();
        assertTrue(operations.stream().anyMatch(op -> op.getId() == 100), "Операция с ID 100 должна быть добавлена");
        assertTrue(operations.stream().anyMatch(op -> op.getId() == 101), "Операция с ID 101 должна быть добавлена");
    }
}

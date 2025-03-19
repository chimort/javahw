package Tests;

import Database.DbProxy;
import Database.InMemoryDb;
import Entities.BankAccount;
import Entities.Category;
import Entities.Operation;
import Entities.Category.CategoryType;
import Entities.Operation.OperationType;
import Exporters.CsvExporter;
import Exporters.JSONExporter;
import Exporters.YamlExporter;
import FinanceFassade.FinanceFassade;
import ImportFileHandler.ImportFileHandler;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FinanceSystemTest {
    private FinanceFassade fassade;
    private final String baseFilename = "test_data";

    @BeforeAll
    void setup() {
        DbProxy dbProxy = new InMemoryDb();
        fassade = new FinanceFassade(dbProxy);
    }

    @Test
    @DisplayName("Тест экспорта в CSV, JSON и YAML")
    void testExport() {
        // Создаём тестовые данные
        fassade.createBankAccount(1, "Test Account", 1000.0);
        fassade.createBankAccount(2, "Savings", 5000.0);

        fassade.createCategory(1, CategoryType.INCOME, "Salary");
        fassade.createCategory(2, CategoryType.CONSUMPTION, "Groceries");

        fassade.createOperation(1, OperationType.INCOME, 1, 2000.0, LocalDate.of(2024, 3, 1), 1);
        fassade.createOperation(2, OperationType.CONSUMPTION, 2, 150.0, LocalDate.of(2024, 3, 2), 2);

        new CsvExporter(baseFilename).exportToFile();
        new JSONExporter(baseFilename + ".json").exportToFile();
        new YamlExporter(baseFilename + ".yaml").exportToFile();

        assertTrue(new File(baseFilename + ".csv").exists(), "CSV файл не создан");
        assertTrue(new File(baseFilename + ".json").exists(), "JSON файл не создан");
        assertTrue(new File(baseFilename + ".yaml").exists(), "YAML файл не создан");
    }

    @Test
    @DisplayName("Тест импорта из CSV")
    void testImportCsv() throws IOException {
        FinanceFassade importFassade = new FinanceFassade(new InMemoryDb());

        ImportFileHandler.importFromCsv(baseFilename + ".csv", importFassade);

        List<BankAccount> accounts = importFassade.getAllBankAccounts();
        List<Category> categories = importFassade.getAllCategories();
        List<Operation> operations = importFassade.getAllOperations();

        assertEquals(2, accounts.size(), "Кол-во счетов не совпадает");
        assertEquals(2, categories.size(), "Кол-во категорий не совпадает");
        assertEquals(2, operations.size(), "Кол-во операций не совпадает");

        // Проверяем правильность данных
        BankAccount acc1 = accounts.get(0);
        assertEquals(1, acc1.getId());
        assertEquals("Test Account", acc1.getName());
        assertEquals(1000.0, acc1.getBalance(), 0.01);

        Operation op1 = operations.get(0);
        assertEquals(1, op1.getId());
        assertEquals(OperationType.INCOME, op1.getType());
        assertEquals(2000.0, op1.getAmount(), 0.01);
    }

    @Test
    @DisplayName("Тест импорта из JSON")
    void testImportJson() {
        FinanceFassade importFassade = new FinanceFassade(new InMemoryDb());

        ImportFileHandler.importFromFile(baseFilename + ".json", importFassade);

        assertEquals(2, importFassade.getAllBankAccounts().size(), "Кол-во счетов в JSON не совпадает");
        assertEquals(2, importFassade.getAllCategories().size(), "Кол-во категорий в JSON не совпадает");
        assertEquals(2, importFassade.getAllOperations().size(), "Кол-во операций в JSON не совпадает");
    }

    @Test
    @DisplayName("Тест импорта из YAML")
    void testImportYaml() {
        FinanceFassade importFassade = new FinanceFassade(new InMemoryDb());

        ImportFileHandler.importFromFile(baseFilename + ".yaml", importFassade);

        assertEquals(2, importFassade.getAllBankAccounts().size(), "Кол-во счетов в YAML не совпадает");
        assertEquals(2, importFassade.getAllCategories().size(), "Кол-во категорий в YAML не совпадает");
        assertEquals(2, importFassade.getAllOperations().size(), "Кол-во операций в YAML не совпадает");
    }

//    @AfterAll
//    void cleanup() {
//        new File(baseFilename + ".csv").delete();
//        new File(baseFilename + ".json").delete();
//        new File(baseFilename + ".yaml").delete();
//    }
}

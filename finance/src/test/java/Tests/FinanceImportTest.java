package Tests;

import Database.InMemoryDb;
import Entities.BankAccount;
import Entities.Category;
import Entities.Operation;
import FinanceFassade.FinanceFassade;
import ImportFileHandler.ImportFileHandler;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FinanceImportTest {
    private FinanceFassade fassadeCsv;
    private FinanceFassade fassadeJson;
    private FinanceFassade fassadeYaml;
    private final String baseFilename = "test_data";

    @BeforeAll
    void setup() {
        // Создаем отдельные системы для импорта из каждого формата
        fassadeCsv = new FinanceFassade(new InMemoryDb());
        fassadeJson = new FinanceFassade(new InMemoryDb());
        fassadeYaml = new FinanceFassade(new InMemoryDb());
    }

    @Test
    @DisplayName("Тест импорта из CSV")
    void testImportFromCsv() throws IOException {
        ImportFileHandler.importFromFile(baseFilename + ".csv", fassadeCsv);

        List<BankAccount> accounts = fassadeCsv.getAllBankAccounts();
        List<Category> categories = fassadeCsv.getAllCategories();
        List<Operation> operations = fassadeCsv.getAllOperations();

        assertEquals(2, accounts.size(), "Кол-во счетов в CSV не совпадает");
        assertEquals(2, categories.size(), "Кол-во категорий в CSV не совпадает");
        assertEquals(2, operations.size(), "Кол-во операций в CSV не совпадает");
    }

    @Test
    @DisplayName("Тест импорта из JSON")
    void testImportFromJson() {
        ImportFileHandler.importFromFile(baseFilename + ".json", fassadeJson);

        assertEquals(2, fassadeJson.getAllBankAccounts().size(), "Кол-во счетов в JSON не совпадает");
        assertEquals(2, fassadeJson.getAllCategories().size(), "Кол-во категорий в JSON не совпадает");
        assertEquals(2, fassadeJson.getAllOperations().size(), "Кол-во операций в JSON не совпадает");
    }

    @Test
    @DisplayName("Тест импорта из YAML")
    void testImportFromYaml() {
        ImportFileHandler.importFromFile(baseFilename + ".yaml", fassadeYaml);

        assertEquals(2, fassadeYaml.getAllBankAccounts().size(), "Кол-во счетов в YAML не совпадает");
        assertEquals(2, fassadeYaml.getAllCategories().size(), "Кол-во категорий в YAML не совпадает");
        assertEquals(2, fassadeYaml.getAllOperations().size(), "Кол-во операций в YAML не совпадает");
    }
}

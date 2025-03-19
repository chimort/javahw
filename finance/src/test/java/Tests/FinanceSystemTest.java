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
import java.nio.file.Files;
import java.nio.file.Paths;
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
    void testExportAllFormats() throws IOException {
        fassade.createBankAccount(1, "Test Account", 1000.0);
        fassade.createBankAccount(2, "Savings", 5000.0);
        fassade.createCategory(1, CategoryType.INCOME, "Salary");
        fassade.createCategory(2, CategoryType.CONSUMPTION, "Groceries");
        fassade.createOperation(1, OperationType.INCOME, 1, 2000.0, LocalDate.of(2024, 3, 1), 1);
        fassade.createOperation(2, OperationType.CONSUMPTION, 2, 150.0, LocalDate.of(2024, 3, 2), 2);

        CsvExporter csvExporter = new CsvExporter(baseFilename);
        fassade.getAllBankAccounts().forEach(csvExporter::visit);
        fassade.getAllCategories().forEach(csvExporter::visit);
        fassade.getAllOperations().forEach(csvExporter::visit);
        csvExporter.exportToFile();

        JSONExporter jsonExporter = new JSONExporter(baseFilename + ".json");
        fassade.getAllBankAccounts().forEach(jsonExporter::visit);
        fassade.getAllCategories().forEach(jsonExporter::visit);
        fassade.getAllOperations().forEach(jsonExporter::visit);
        jsonExporter.exportToFile();

        YamlExporter yamlExporter = new YamlExporter(baseFilename + ".yaml");
        fassade.getAllBankAccounts().forEach(yamlExporter::visit);
        fassade.getAllCategories().forEach(yamlExporter::visit);
        fassade.getAllOperations().forEach(yamlExporter::visit);
        yamlExporter.exportToFile();

        assertTrue(new File(baseFilename + ".csv").exists(), "CSV файл не создан");
        assertTrue(new File(baseFilename + ".json").exists(), "JSON файл не создан");
        assertTrue(new File(baseFilename + ".yaml").exists(), "YAML файл не создан");

        assertTrue(checkFileContainsData(baseFilename + ".csv"), "CSV файл пуст");
        assertTrue(checkFileContainsData(baseFilename + ".json"), "JSON файл пуст");
        assertTrue(checkFileContainsData(baseFilename + ".yaml"), "YAML файл пуст");
    }

    private boolean checkFileContainsData(String filename) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(filename));
            return lines.size() > 1;
        } catch (IOException e) {
            return false;
        }
    }

    @AfterAll
    void cleanup() {
        new File(baseFilename + ".csv").delete();
        new File(baseFilename + ".json").delete();
        new File(baseFilename + ".yaml").delete();
    }
}

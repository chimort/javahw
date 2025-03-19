package ImportFileHandler;

import Entities.BankAccount;
import Entities.Category;
import Entities.Operation;
import FinanceFassade.FinanceFassade;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class ImportFileHandler {
    public static void importFromFile(String filename, FinanceFassade fassade) {
        try {
            String content = new String(Files.readAllBytes(Paths.get(filename)));

            if (filename.endsWith(".json")) {
                importFromJson(content, fassade);
            } else if (filename.endsWith(".yaml") || filename.endsWith(".yml")) {
                importFromYaml(filename, fassade);
            } else if (filename.endsWith(".csv")) {
                importFromCsv(filename, fassade);
            } else {
                System.out.println("Неизвестный формат файла.");
            }
        } catch (IOException e) {
            System.out.println("Ошибка при импорте данных: " + e.getMessage());
        }
    }

    public static void importFromJson(String json, FinanceFassade fassade) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        DataWrapper data = objectMapper.readValue(json, DataWrapper.class);
        addDataToFassade(data, fassade);
    }

    public static void importFromYaml(String filename, FinanceFassade fassade) {
        Yaml yaml = new Yaml();
        try (FileReader reader = new FileReader(filename)) {
            DataWrapper data = yaml.loadAs(reader, DataWrapper.class);
            addDataToFassade(data, fassade);
        } catch (IOException e) {
            System.out.println("Ошибка при чтении YAML: " + e.getMessage());
        }
    }

    public static void importFromCsv(String filename, FinanceFassade fassade) throws IOException {
        CsvMapper csvMapper = new CsvMapper();
        CsvSchema schema = CsvSchema.builder()
                .addColumn("entityType")
                .addColumn("id")
                .addColumn("nameOrType")
                .addColumn("amountOrBalance")
                .addColumn("dateOrNull")
                .addColumn("bankAccountIdOrNull")
                .addColumn("categoryIdOrNull")
                .setUseHeader(true)
                .build();

        // Читаем CSV как List<Object>, а потом кастуем к Map<String, Object>
        List<Object> rawRows = csvMapper.readerFor(Map.class)
                .with(schema)
                .readValues(Paths.get(filename).toFile())
                .readAll();

        for (Object rowObj : rawRows) {
            try {
                Map<String, Object> row = (Map<String, Object>) rowObj;

                if (!row.containsKey("entityType")) {
                    System.out.println("Пропущена строка без entityType: " + row);
                    continue;
                }

                String entityType = row.get("entityType").toString().trim();
                int id = Integer.parseInt(row.get("id").toString().trim());

                switch (entityType) {
                    case "BankAccount":
                        String name = row.get("nameOrType").toString().trim();
                        double balance = Double.parseDouble(row.get("amountOrBalance").toString().trim());
                        fassade.createBankAccount(id, name, balance);
                        break;

                    case "Category":
                        String categoryName = row.get("nameOrType").toString().trim();
                        Category.CategoryType type = Category.CategoryType.valueOf(row.get("amountOrBalance").toString().trim().toUpperCase());
                        fassade.createCategory(id, type, categoryName);
                        break;

                    case "Operation":
                        Operation.OperationType opType = Operation.OperationType.valueOf(row.get("nameOrType").toString().trim().toUpperCase());
                        double amount = Double.parseDouble(row.get("amountOrBalance").toString().trim());
                        LocalDate date = LocalDate.parse(row.get("dateOrNull").toString().trim(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                        int bankAccountId = Integer.parseInt(row.get("bankAccountIdOrNull").toString().trim());
                        int categoryId = Integer.parseInt(row.get("categoryIdOrNull").toString().trim());
                        fassade.createOperation(id, opType, bankAccountId, amount, date, categoryId);
                        break;

                    default:
                        System.out.println("Неизвестный entityType: " + entityType);
                }
            } catch (Exception e) {
                System.out.println("Ошибка при обработке строки: " + rowObj);
            }
        }

        System.out.println("CSV-файл успешно импортирован.");
    }

    private static void addDataToFassade(DataWrapper data, FinanceFassade fassade) {
        for (BankAccount acc : data.getBankAccounts()) {
            fassade.createBankAccount(acc.getId(), acc.getName(), acc.getBalance());
        }

        for (Category cat : data.getCategories()) {
            fassade.createCategory(cat.getId(), cat.getCategoryType(), cat.getName());
        }

        for (Operation op : data.getOperations()) {
            fassade.createOperation(
                    op.getId(), op.getType(), op.getBankAccount().getId(),
                    op.getAmount(), op.getDate(), op.getCategory().getId()
            );
        }

        System.out.println("Импорт завершён.");
    }


    static class DataWrapper {
        private List<BankAccount> bankAccounts;
        private List<Category> categories;
        private List<Operation> operations;

        public List<BankAccount> getBankAccounts() { return bankAccounts; }
        public List<Category> getCategories() { return categories; }
        public List<Operation> getOperations() { return operations; }
    }
}

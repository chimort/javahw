import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import Entities.BankAccount;
import Entities.Category;
import Entities.Category.CategoryType;
import DI.AppModule;
import Entities.Operation;
import Entities.Operation.OperationType;
import Commands.*;
import Database.DbProxy;
import FinanceFassade.FinanceFassade;
import Visitor.ExportVisitor;
import Exporters.CsvExporter;
import Exporters.JSONExporter;
import Exporters.YamlExporter;
import ImportFileHandler.ImportFileHandler;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class Main {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new AppModule());

        FinanceFassade fassade = injector.getInstance(FinanceFassade.class);
        DbProxy dbProxy = injector.getInstance(DbProxy.class);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Финансовый учёт ===");
            System.out.println("1. Создать счёт");
            System.out.println("2. Создать категорию");
            System.out.println("3. Создать операцию");
            System.out.println("4. Показать список счетов");
            System.out.println("5. Показать список категорий");
            System.out.println("6. Показать список операций");
            System.out.println("7. Экспорт данных");
            System.out.println("8. Импорт данных");
            System.out.println("9. Редактировать счёт");
            System.out.println("10. Редактировать категорию");
            System.out.println("11. Редактировать операцию");
            System.out.println("12. Удалить счёт");
            System.out.println("13. Удалить категорию");
            System.out.println("14. Удалить операцию");
            System.out.println("0. Выход");
            System.out.print("Выберите опцию: ");

            int choice = getIntInput(scanner);
            if (choice == 0) break;

            switch (choice) {
                case 1:
                    createBankAccount(scanner, fassade);
                    break;
                case 2:
                    createCategory(scanner, fassade);
                    break;
                case 3:
                    createOperation(scanner, fassade);
                    break;
                case 4:
                    showBankAccounts(fassade);
                    break;
                case 5:
                    showCategories(fassade);
                    break;
                case 6:
                    showOperations(fassade);
                    break;
                case 7:
                    exportData(scanner, fassade);
                    break;
                case 8:
                    importData(scanner, fassade);
                    break;
                case 9:
                    updateBankAccount(scanner, fassade);
                    break;
                case 10:
                    updateCategory(scanner, fassade);
                    break;
                case 11:
                    updateOperation(scanner, fassade);
                    break;
                case 12:
                    deleteBankAccount(scanner, fassade);
                    break;
                case 13:
                    deleteCategory(scanner, fassade);
                    break;
                case 14:
                    deleteOperation(scanner, fassade);
                    break;
                default:
                    System.out.println("Неверная опция. Попробуйте снова.");
            }
        }

        scanner.close();
        System.out.println("Выход из приложения.");
    }

    private static void updateBankAccount(Scanner scanner, FinanceFassade fassade) {
        while (true) {
            try {
                int accountId = getIntInput(scanner, "Введите ID счёта для редактирования: ");
                String accountName = getStringInput(scanner, "Введите новое название счёта: ");
                double balance = getDoubleInput(scanner, "Введите новый баланс: ");

                Command updateCmd = new UpdateCommand(fassade, accountId,"account", accountName, balance);
                Command timedUpdate = new CommandDecorator(updateCmd);
                timedUpdate.execute();

                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void updateCategory(Scanner scanner, FinanceFassade fassade) {
        while (true) {
            try {
                int categoryId = getIntInput(scanner, "Введите ID категории для редактирования: ");
                String categoryName = getStringInput(scanner, "Введите новое название категории: ");

                Command updateCmd = new UpdateCommand(fassade, categoryId, "category", categoryName);
                Command timedUpdate = new CommandDecorator(updateCmd);
                timedUpdate.execute();

                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void updateOperation(Scanner scanner, FinanceFassade fassade) {
        while (true) {
            try {
                int operationId = getIntInput(scanner, "Введите ID операции для редактирования: ");
                OperationType opType = getEnumInput(scanner, "Введите новый тип операции (INCOME/CONSUMPTION): ", OperationType.class);
                int accId = getIntInput(scanner, "Введите новый ID счёта: ");
                double amount = getDoubleInput(scanner, "Введите новую сумму операции: ");
                LocalDate date = getDateInput(scanner, "Введите новую дату операции (dd-MM-yyyy): ");
                int catId = getIntInput(scanner, "Введите новый ID категории: ");

                Command updateCmd = new UpdateCommand(fassade, operationId, "operation", opType, amount, date, accId, catId);
                Command timedUpdate = new CommandDecorator(updateCmd);
                timedUpdate.execute();

                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void deleteBankAccount(Scanner scanner, FinanceFassade fassade) {
        int accountId = getIntInput(scanner, "Введите ID счёта для удаления: ");
        Command deleteCmd = new DeleteCommand(fassade, accountId, "account");
        Command timedDelete = new CommandDecorator(deleteCmd);
        timedDelete.execute();
    }

    private static void deleteCategory(Scanner scanner, FinanceFassade fassade) {
        int categoryId = getIntInput(scanner, "Введите ID категории для удаления: ");
        Command deleteCmd = new DeleteCommand(fassade, categoryId, "category");
        Command timedDelete = new CommandDecorator(deleteCmd);
        timedDelete.execute();
    }

    private static void deleteOperation(Scanner scanner, FinanceFassade fassade) {
        int operationId = getIntInput(scanner, "Введите ID операции для удаления: ");
        Command deleteCmd = new DeleteCommand(fassade, operationId, "operation");
        Command timedDelete = new CommandDecorator(deleteCmd);
        timedDelete.execute();
    }

    private static void createBankAccount(Scanner scanner, FinanceFassade fassade) {
        while (true) {
            try {
                int accountId = getIntInput(scanner, "Введите ID счёта: ");
                String accountName = getStringInput(scanner, "Введите название счёта: ");
                double balance = getDoubleInput(scanner, "Введите начальный баланс: ");
                Command createAccountCmd = new CreateBankAccountCommand(fassade, accountId, accountName, balance);
                Command timedCreateAccount = new CommandDecorator(createAccountCmd);
                timedCreateAccount.execute();
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void createCategory(Scanner scanner, FinanceFassade fassade) {
        while (true) {
            try {
                int categoryId = getIntInput(scanner, "Введите ID категории: ");
                String categoryName = getStringInput(scanner, "Введите название категории: ");
                CategoryType type = getEnumInput(scanner, "Введите тип категории (INCOME/CONSUMPTION): ", CategoryType.class);
                Command createCategoryCmd = new CreateCategoryCommand(fassade, categoryId, type, categoryName);
                Command timedCreateCategory = new CommandDecorator(createCategoryCmd);
                timedCreateCategory.execute();
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void createOperation(Scanner scanner, FinanceFassade fassade) {
        while (true) {
            try {
                int operationId = getIntInput(scanner, "Введите ID операции: ");
                OperationType opType = getEnumInput(scanner, "Введите тип операции (INCOME/CONSUMPTION): ", OperationType.class);
                int accId = getIntInput(scanner, "Введите ID счёта: ");
                double amount = getDoubleInput(scanner, "Введите сумму операции: ");
                LocalDate date = getDateInput(scanner, "Введите дату операции (dd-MM-yyyy): ");
                int catId = getIntInput(scanner, "Введите ID категории: ");
                Command createOperationCmd = new CreateOperationCommand(fassade, operationId, opType, accId, amount, date, catId);
                Command timedCreateOperation = new CommandDecorator(createOperationCmd);
                timedCreateOperation.execute();
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void showBankAccounts(FinanceFassade fassade) {
        System.out.println("Список счетов:");
        for (BankAccount acc : fassade.getAllBankAccounts()) {
            System.out.println(acc.getId() + ": " + acc.getName() + ", Баланс: " + acc.getBalance());
        }
    }

    private static void showCategories(FinanceFassade fassade) {
        System.out.println("Список категорий:");
        for (Category cat : fassade.getAllCategories()) {
            System.out.println(cat.getId() + ": " + cat.getName() + ", Тип: " + cat.getCategoryType());
        }
    }

    private static void showOperations(FinanceFassade fassade) {
        System.out.println("Список операций:");
        for (Operation op : fassade.getAllOperations()) {
            System.out.println(op.getId() + ": " + op.getType() + ", Сумма: " + op.getAmount() +
                    ", Дата: " + op.getDate() + ", Счёт: " + op.getBankAccount().getName() +
                    ", Категория: " + op.getCategory().getName());
        }
    }

    private static void exportData(Scanner scanner, FinanceFassade fassade) {
        System.out.print("Выберите формат экспорта (csv/json/yaml): ");
        String format = scanner.nextLine().trim().toLowerCase();
        System.out.print("Введите имя файла для экспорта: ");
        String filename = scanner.nextLine().trim();

        ExportVisitor visitor;
        switch (format) {
            case "csv":
                visitor = new CsvExporter(filename);
                break;
            case "json":
                visitor = new JSONExporter(filename + ".json");
                break;
            case "yaml":
                visitor = new YamlExporter(filename);
                break;
            default:
                System.out.println("Неверный формат экспорта.");
                return;
        }

        fassade.getAllBankAccounts().forEach(visitor::visit);
        fassade.getAllCategories().forEach(visitor::visit);
        fassade.getAllOperations().forEach(visitor::visit);

        visitor.exportToFile();
    }


    private static void importData(Scanner scanner, FinanceFassade fassade) {
        System.out.print("Введите имя файла для импорта: ");
        String filename = scanner.nextLine().trim();

        try {
            String fileExtension = filename.substring(filename.lastIndexOf('.') + 1).toLowerCase();

            switch (fileExtension) {
                case "csv":
                    ImportFileHandler.importFromCsv(filename, fassade);
                    break;
                case "json":
                    String jsonContent = new String(Files.readAllBytes(Paths.get(filename)));
                    ImportFileHandler.importFromJson(jsonContent, fassade);
                    break;
                case "yaml":
                case "yml":
                    ImportFileHandler.importFromYaml(filename, fassade);
                    break;
                default:
                    System.out.println("Ошибка: неподдерживаемый формат файла.");
                    return;
            }

            System.out.println("Импорт завершён успешно.");
        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
        }
    }


    private static int getIntInput(Scanner scanner, String message) {
        while (true) {
            System.out.print(message);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите целое число.");
            }
        }
    }

    private static int getIntInput(Scanner scanner) {
        return getIntInput(scanner, "");
    }

    private static double getDoubleInput(Scanner scanner, String message) {
        while (true) {
            System.out.print(message);
            try {
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите корректное число.");
            }
        }
    }

    private static LocalDate getDateInput(Scanner scanner, String message) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        while (true) {
            System.out.print(message);
            try {
                return LocalDate.parse(scanner.nextLine().trim(), formatter);
            } catch (Exception e) {
                System.out.println("Ошибка: неверный формат даты. Используйте dd-MM-yyyy.");
            }
        }
    }

    private static <T extends Enum<T>> T getEnumInput(Scanner scanner, String message, Class<T> enumType) {
        while (true) {
            System.out.print(message);
            try {
                return Enum.valueOf(enumType, scanner.nextLine().trim().toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка: неверное значение. Попробуйте снова.");
            }
        }
    }

    private static String getStringInput(Scanner scanner, String message) {
        System.out.print(message);
        return scanner.nextLine().trim();
    }
}

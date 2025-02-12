import Animals.*;
import DI.ZooModule;
import Inventory.Computer;
import Inventory.Table;
import Services.Clinic;
import Services.Zoo;
import com.google.inject.Guice;
import com.google.inject.Injector;

import java.util.List;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new ZooModule());
        Zoo zoo = injector.getInstance(Zoo.class);
        Clinic clinic = injector.getInstance(Clinic.class);
        Scanner scanner = new Scanner(System.in);

        boolean running = true;
        while (running) {
            System.out.println("\n--- Меню ---");
            System.out.println("1. Добавить животное");
            System.out.println("2. Показать отчёт по животным и потреблению еды");
            System.out.println("3. Показать список животных для контактного зоопарка");
            System.out.println("4. Показать инвентаризацию вещей");
            System.out.println("5. Добавить вещь");
            System.out.println("6. Выход");
            System.out.print("Выберите опцию: ");
            String option = scanner.nextLine();
            switch (option) {
                case "1":
                    addAnimalFlow(zoo, clinic, scanner);
                    break;
                case "2":
                    System.out.println("\nОбщее количество животных: " + zoo.getAnimals().size());
                    System.out.println("Общее количество килограммов еды в день: " + zoo.getTotalFoodConsumption());
                    break;
                case "3":
                    List<Animal> contactAnimals = zoo.getAnimalsForContactZoo();
                    if (contactAnimals.isEmpty()) {
                        System.out.println("\nНет животных, подходящих для контактного зоопарка.");
                    } else {
                        System.out.println("\nЖивотные для контактного зоопарка:");
                        for (Animal a : contactAnimals) {
                            System.out.println("Инв. номер: " + a.getNumber() +
                                    ", Тип: " + a.getType() +
                                    ", Имя: " + a.getName());
                        }
                    }
                    break;
                case "4":
                    System.out.println();
                    zoo.printInventory();
                    break;
                case "5":
                    addInventoryFlow(zoo, scanner);
                    break;
                case "6":
                    running = false;
                    break;
                default:
                    System.out.println("Неверная опция, попробуйте снова.");
            }
        }
        System.out.println("Выход из программы.");
    }

    private static void addAnimalFlow(Zoo zoo, Clinic clinic, Scanner scanner) {
        System.out.println("\nВыберите тип животного для добавления:");
        System.out.println("1. Обезьяна (Monkey) [Herbo]");
        System.out.println("2. Кролик (Rabbit) [Herbo]");
        System.out.println("3. Тигр (Tiger) [Predator]");
        System.out.println("4. Волк (Wolf) [Predator]");
        System.out.print("Опция: ");
        String choice = scanner.nextLine();
        System.out.print("Введите имя животного: ");
        String name = scanner.nextLine();
        System.out.print("Введите количество потребляемой еды (кг): ");
        int food = Integer.parseInt(scanner.nextLine());
        Animal animal = null;
        switch (choice) {
            case "1":
                System.out.print("Введите уровень доброты (0-10): ");
                int kindnessMonkey = Integer.parseInt(scanner.nextLine());
                animal = new Monkey(name, food, kindnessMonkey);
                break;
            case "2":
                System.out.print("Введите уровень доброты (0-10): ");
                int kindnessRabbit = Integer.parseInt(scanner.nextLine());
                animal = new Rabbit(name, food, kindnessRabbit);
                break;
            case "3":
                animal = new Tiger(name, food);
                break;
            case "4":
                animal = new Wolf(name, food);
                break;
            default:
                System.out.println("Неверный выбор типа животного.");
                return;
        }
        boolean healthy = clinic.checkHealth(animal);
        if (healthy) {
            zoo.addAnimal(animal);
            System.out.println("Животное успешно добавлено в зоопарк.");
        } else {
            System.out.println("Животное не прошло проверку здоровья и не добавлено.");
        }
    }

    private static void addInventoryFlow(Zoo zoo, Scanner scanner) {
        System.out.println("\nВыберите тип вещи для добавления:");
        System.out.println("1. Стол (Table)");
        System.out.println("2. Компьютер (Computer)");
        System.out.print("Опция: ");
        String choice = scanner.nextLine();
        System.out.print("Введите наименование вещи: ");
        String name = scanner.nextLine();
        switch (choice) {
            case "1":
                zoo.addInventoryItem(new Table(name));
                break;
            case "2":
                zoo.addInventoryItem(new Computer(name));
                break;
            default:
                System.out.println("Неверный выбор типа вещи.");
                return;
        }
        System.out.println("Вещь успешно добавлена.");
    }
}

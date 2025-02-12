package Services;

import Animals.Animal;

import java.util.Scanner;

public class Clinic {
    final private Scanner scanner_;

    public Clinic() {
        scanner_ = new Scanner(System.in);
    }

    public boolean checkHealth(Animal animal) {
        System.out.println("\nПроверка здоровья животного: " + animal.getType() + " по имени " + animal.getName());
        System.out.print("Животное здорово? (y/n): ");
        String input = scanner_.nextLine();
        boolean healthy = input.trim().equalsIgnoreCase("y");
        animal.setHealthy(healthy);
        return healthy;
    }
}

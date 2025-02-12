package Services;

import Animals.Animal;
import Animals.Herbo;
import Inventory.Thing;

import java.util.ArrayList;
import java.util.List;

public class Zoo {
    private final List<Animal> animals_;
    private final List<Thing> inventory_;

    public Zoo() {
        animals_ = new ArrayList<>();
        inventory_ = new ArrayList<>();
    }

    public void addAnimal(Animal animal) {
        animals_.add(animal);
    }

    public List<Animal> getAnimals() {
        return animals_;
    }

    public List<Thing> getInventory() { return inventory_; }

    public void addInventoryItem(Thing item) {
        inventory_.add(item);
    }

    public int getTotalFoodConsumption() {
        int total = 0;
        for (Animal a : animals_) {
            total += a.getFood();
        }
        return total;
    }

    public List<Animal> getAnimalsForContactZoo() {
        List<Animal> res = new ArrayList<>();
        for (Animal a : animals_) {
            if (a instanceof Herbo h) {
                if (h.getKindness() > 5) {
                    res.add(a);
                }
            }
        }
        return res;
    }

    public void printAnimals() {
        if (animals_.isEmpty()) {
            System.out.println("Нет животных");
        } else {
            for (Animal a : animals_) {
                System.out.println("Животное: " + a.getNumber() + "\nТипа: " + a.getType() + "\nС именем: " + a.getName());
            }
        }
    }

    public void printInventory() {
        if (inventory_.isEmpty()) {
            System.out.println("Нет вещей");
        } else {
            for (Thing t : inventory_) {
                System.out.printf("Предмет №%d\n%s", t.getNumber(), t.getName());
            }
        }
    }
}

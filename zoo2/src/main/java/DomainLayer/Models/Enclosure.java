package DomainLayer.Models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Enclosure {
    private final UUID id;
    private String type;
    private double size;
    private int capacity;
    private List<UUID> animals_id;

    public Enclosure(String type, double size, int capacity) {
        this.id = UUID.randomUUID();
        this.type = type;
        this.size = size;
        this.capacity = capacity;
        this.animals_id = new ArrayList<>();
    }

    public UUID getId() {
        return id;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public double getSize() {
        return size;
    }
    public void setSize(double size) {
        this.size = size;
    }
    public int getMaxCapacity() {
        return capacity;
    }
    public void setMaxCapacity(int maxCapacity) {
        this.capacity = maxCapacity;
    }
    public List<UUID> getAnimalIds() {
        return animals_id;
    }

    public void addAnimal(UUID animalId) {
        if (animals_id.size() < capacity) {
            animals_id.add(animalId);
        } else {
            throw new IllegalStateException("В вольере нет места");
        }
    }

    public void removeAnimal(UUID animalId) {
        animals_id.remove(animalId);
    }

    public void clean() {
        System.out.println("В вольере " + this.id + " была проведена уборка");
    }
}

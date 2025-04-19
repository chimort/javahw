package ApplicationLayer.Interfaces;

import DomainLayer.Models.Animal;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AnimalRepository {
    void add(Animal animal);
    void remove(UUID animal_id);
    Optional<Animal> findById(UUID id);
    List<Animal> findAll();
    void update(Animal animal);
}

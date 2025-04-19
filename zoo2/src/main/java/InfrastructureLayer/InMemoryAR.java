package InfrastructureLayer;

import ApplicationLayer.Interfaces.AnimalRepository;
import DomainLayer.Models.Animal;

import java.util.*;

public class InMemoryAR implements AnimalRepository {
    private final Map<UUID, Animal> store = new HashMap<>();

    @Override
    public void add(Animal animal) {
        store.put(animal.getId(), animal);
    }

    @Override
    public void remove(UUID id) {
        store.remove(id);
    }

    @Override
    public Optional<Animal> findById(UUID id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Animal> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void update(Animal animal) {
        store.put(animal.getId(), animal);
    }
}

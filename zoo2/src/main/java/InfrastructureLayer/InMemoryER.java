package InfrastructureLayer;

import ApplicationLayer.Interfaces.EnclosureRepository;
import DomainLayer.Models.Enclosure;

import java.util.*;

public class InMemoryER implements EnclosureRepository {
    private final Map<UUID, Enclosure> store = new HashMap<>();

    @Override
    public void add(Enclosure enclosure) {
        store.put(enclosure.getId(), enclosure);
    }

    @Override
    public void remove(UUID id) {
        store.remove(id);
    }

    @Override
    public Optional<Enclosure> findById(UUID id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Enclosure> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void update(Enclosure enclosure) {
        store.put(enclosure.getId(), enclosure);
    }
}

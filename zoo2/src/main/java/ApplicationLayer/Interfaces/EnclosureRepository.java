package ApplicationLayer.Interfaces;

import DomainLayer.Models.Enclosure;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EnclosureRepository {
    void add(Enclosure enclosure);
    void remove(UUID id);
    Optional<Enclosure> findById(UUID id);
    List<Enclosure> findAll();
    void update(Enclosure enclosure);
}

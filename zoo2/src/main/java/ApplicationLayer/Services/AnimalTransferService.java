package ApplicationLayer.Services;

import ApplicationLayer.Interfaces.AnimalRepository;
import ApplicationLayer.Interfaces.EnclosureRepository;
import DomainLayer.Events.AnimalMovedEvent;
import DomainLayer.Models.Animal;
import DomainLayer.Models.Enclosure;

import java.util.UUID;

public class AnimalTransferService {
    private final AnimalRepository animal_rep;
    private final EnclosureRepository enclosure_rep;

    public AnimalTransferService(AnimalRepository animal_rep, EnclosureRepository enclosure_rep) {
        this.animal_rep = animal_rep;
        this.enclosure_rep = enclosure_rep;
    }

    public AnimalMovedEvent transferAnimals(UUID animal_id, UUID to_enclosure_id) {
        Animal animal = animal_rep.findById(animal_id).orElseThrow(() -> new IllegalArgumentException("Животное не найдено"));
        UUID from_enclosure_id = animal.getEnclosureId();
        Enclosure from_enclosure = enclosure_rep.findById(from_enclosure_id).orElseThrow(
                () -> new IllegalArgumentException("Вальер не найден"));
        Enclosure to_enclosure = enclosure_rep.findById(to_enclosure_id).orElseThrow(
                () -> new IllegalArgumentException("Валье р не найдет"));

        from_enclosure.removeAnimal(animal_id);
        to_enclosure.addAnimal(animal_id);
        animal.moveToEnclosure(to_enclosure_id);

        animal_rep.update(animal);
        enclosure_rep.update(from_enclosure);
        enclosure_rep.update(to_enclosure);

        return new AnimalMovedEvent(animal_id, from_enclosure_id, to_enclosure_id);
    }
}

package DomainLayer.Events;

import java.util.UUID;

public class AnimalMovedEvent {
    private final UUID animal_id;
    private final UUID from_enclosure;
    private final UUID to_enclosure;

    public AnimalMovedEvent(UUID animal_id, UUID from_enclosure, UUID to_enclosure) {
        this.animal_id = animal_id;
        this.from_enclosure = from_enclosure;
        this.to_enclosure = to_enclosure;
    }

    public UUID getAnimalId() {
        return animal_id;
    }
    public UUID getFromEnclosureId() {
        return from_enclosure;
    }
    public UUID getToEnclosureId() {
        return to_enclosure;
    }
}

import ApplicationLayer.Interfaces.AnimalRepository;
import ApplicationLayer.Interfaces.EnclosureRepository;
import ApplicationLayer.Services.AnimalTransferService;
import DomainLayer.Events.AnimalMovedEvent;
import DomainLayer.Models.Animal;
import DomainLayer.Models.Enclosure;
import DomainLayer.Models.Gender;
import DomainLayer.Models.HealthStatus;
import InfrastructureLayer.InMemoryAR;
import InfrastructureLayer.InMemoryER;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

public class AnimalTransferServiceTest {

    private AnimalRepository animalRepo;
    private EnclosureRepository enclosureRepo;
    private AnimalTransferService transferService;

    @BeforeEach
    public void setup() {
        animalRepo = new InMemoryAR();
        enclosureRepo = new InMemoryER();
        transferService = new AnimalTransferService(animalRepo, enclosureRepo);

        // Создаём два вольера
        Enclosure enclosure1 = new Enclosure("Herbivores", 500, 10);
        Enclosure enclosure2 = new Enclosure("Carnivores", 300, 5);
        enclosureRepo.add(enclosure1);
        enclosureRepo.add(enclosure2);

        // Создаём животное и добавляем в enclosure1
        Animal animal = new Animal("Giraffe", "Melman", LocalDate.of(2015, 5, 1), Gender.MALE, "Leaves", HealthStatus.HEALTH);
        animal.setEnclosureId(enclosure1.getId());
        animalRepo.add(animal);
        enclosure1.addAnimal(animal.getId());
        enclosureRepo.update(enclosure1);
    }

    @Test
    public void testTransferAnimal() {
        Animal animal = animalRepo.findAll().get(0);
        Enclosure fromEnclosure = enclosureRepo.findAll().get(0);
        Enclosure toEnclosure = enclosureRepo.findAll().get(1);

        AnimalMovedEvent event = transferService.transferAnimals(animal.getId(), toEnclosure.getId());
        Assertions.assertEquals(toEnclosure.getId(), animal.getEnclosureId());
        Assertions.assertFalse(fromEnclosure.getAnimalIds().contains(animal.getId()));
        Assertions.assertTrue(toEnclosure.getAnimalIds().contains(animal.getId()));
        Assertions.assertNotNull(event);
    }
}
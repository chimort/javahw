package PresentationLayer;

import ApplicationLayer.Interfaces.AnimalRepository;
import DomainLayer.Models.Animal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/animals")
public class AnimalController {
    private final AnimalRepository animal_rep;

    public AnimalController(AnimalRepository animal_rep) {
        this.animal_rep = animal_rep;
    }

    @GetMapping
    public List<Animal> getAllAnimals() {
        return animal_rep.findAll();
    }

    @PostMapping
    public Animal addAnimal(@RequestBody Animal animal) {
        animal_rep.add(animal);
        return animal;
    }

    @DeleteMapping("/{id}")
    public void deleteAnimal(@PathVariable UUID id) {
        animal_rep.remove(id);
    }
}

import Animals.Wolf;
import Services.Clinic;
import Services.Zoo;
import Animals.Monkey;
import Animals.Animal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ZooTest {
    private Zoo zoo;
    private Clinic clinic;

    @BeforeEach
    void setUp() {
        clinic = new Clinic() {
            @Override
            public boolean checkHealth(Animal animal) {
                return true;
            }
        };
        zoo = new Zoo();
    }

    @Test
    void testAddAnimal() {
        Animal monkey = new Monkey("Умар", 5, 8);

        zoo.addAnimal(monkey);

        assertEquals(1, zoo.getAnimals().size());
        assertEquals(5, monkey.getFood());
    }

    @Test
    void testAddAnimalWolf() {
        Animal wolf = new Wolf("Волк", 5);

        zoo.addAnimal(wolf);

        assertEquals(1, zoo.getAnimals().size());
    }
}

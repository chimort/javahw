import Animals.Monkey;
import Services.Clinic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ClinicTests {

    private Clinic clinic;

    @BeforeEach
    void setUp() {
        clinic = new Clinic();
    }

    @Test
    void testCheckHealth() {
        Monkey monkey = new Monkey("Обезьяна", 5, 8);

        assertFalse(monkey.isHealthy());

        monkey.setHealthy(true);
        boolean healthy = monkey.isHealthy();

        assertTrue(healthy);
        assertTrue(monkey.isHealthy());
    }
}

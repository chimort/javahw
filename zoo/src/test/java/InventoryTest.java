import Inventory.Computer;
import Inventory.Table;
import Services.Zoo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InventoryTest {
    private Zoo zoo;

    @BeforeEach
    void setUp() {
        zoo = new Zoo();
    }

    @Test
    void testAddTable() {
        Table table = new Table("стол");
        zoo.addInventoryItem(table);

        assertEquals(1, zoo.getInventory().size());
        assertTrue(zoo.getInventory().contains(table));
    }

    @Test
    void testAddComputer() {
        Computer pc = new Computer("MacBook");
        zoo.addInventoryItem(pc);

        assertEquals(1, zoo.getInventory().size());
        assertTrue(zoo.getInventory().contains(pc));
    }
}

package Inventory;

public class Thing implements IInventory {
    protected String name_;
    protected int number_;
    private static int next_number_ = 1;

    public Thing(String name) {
        this.name_ = name;
        this.number_ = next_number_++;
    }

    @Override
    public int getNumber() {
        return 0;
    }

    public String getName() {
        return name_;
    }
}

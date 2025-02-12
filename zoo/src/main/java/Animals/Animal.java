package Animals;

import Animals.IAlive;
import Inventory.IInventory;

public abstract class Animal implements IAlive, IInventory {
    protected String name_;
    protected int food_;
    protected int inventory_number_;
    protected boolean  is_healthy_;
    private static int next_inv_number_ = 1;

    public Animal(String name, int food) {
        this.name_ = name;
        this.food_ = food;
        this.inventory_number_ = next_inv_number_++;
        this.is_healthy_ = false;
    }

    @Override
    public int getFood() {
        return food_;
    }

    @Override
    public int getNumber() {
        return inventory_number_;
    }

    public String getName() {
        return name_;
    }

    public boolean isHealthy() {
        return is_healthy_;
    }

    public void setHealthy(boolean healthy) {
        this.is_healthy_ = healthy;
    }

    public abstract String getType();
}

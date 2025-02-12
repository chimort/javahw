package Animals;

public abstract class Herbo extends Animal {
    protected int kindness_;

    public Herbo(String name, int food, int kindness) {
        super(name, food);
        this.kindness_ = kindness;
    }

    public int getKindness() {
        return kindness_;
    }
}

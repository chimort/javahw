package Animals;

public class Wolf extends Predator{
    public Wolf(String name, int food) {
        super(name, food);
    }

    @Override
    public String getType() {
        return "Волк \uD83D\uDC3A";
    }
}

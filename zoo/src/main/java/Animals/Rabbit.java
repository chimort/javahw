package Animals;

public class Rabbit extends Herbo {
    public Rabbit(String name, int food, int kindness) {
        super(name, food, kindness);
    }

    @Override
    public String getType() {
        return "Кролик \uD83D\uDC07";
    }
}

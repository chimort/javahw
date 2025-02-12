package Animals;

public class Monkey extends Herbo{
    public Monkey(String name, int food, int kindness) {
        super(name, food, kindness);
    }

    @Override
    public String getType() {
        return "Обезьяна \uD83D\uDC35";
    }
}

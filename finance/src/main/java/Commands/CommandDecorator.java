package Commands;

public class CommandDecorator implements Command {
    private Command wrapper_;

    public CommandDecorator(Command wrapper) {
        this.wrapper_ = wrapper;
    }

    @Override
    public  void execute() {
        long start = System.currentTimeMillis();
        wrapper_.execute();
        long end = System.currentTimeMillis();
        System.out.println("Время выполнения: " + (end - start) + " мс");
    }
}

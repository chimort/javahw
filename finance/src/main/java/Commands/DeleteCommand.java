package Commands;

import FinanceFassade.FinanceFassade;

public class DeleteCommand implements Command {
    private final FinanceFassade fassade_;
    private final int id_;
    private final String type_;

    public DeleteCommand(FinanceFassade fassade, int id, String type) {
        this.fassade_ = fassade;
        this.id_ = id;
        this.type_ = type;
    }

    @Override
    public void execute() {
        switch (type_) {
            case "account":
                fassade_.deleteBankAccount(id_);
                break;
            case "category":
                fassade_.deleteCategory(id_);
                break;
            case "operation":
                fassade_.deleteOperation(id_);
                break;
            default:
                throw new IllegalArgumentException("Неизвестный тип: " + type_);
        }
        System.out.println(type_ + " с ID " + id_ + " удален");
    }
}

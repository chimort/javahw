package Commands;

import Entities.Category;
import Entities.Operation;
import FinanceFassade.FinanceFassade;
import java.time.LocalDate;

public class UpdateCommand implements Command {
    private final FinanceFassade fassade_;
    private final int id_;
    private final String type_;
    private final Object[] params_;

    public UpdateCommand(FinanceFassade fassade, int id, String type, Object... params) {
        this.fassade_ = fassade;
        this.id_ = id;
        this.type_ = type;
        this.params_ = params;
    }

    @Override
    public void execute() {
        switch (type_) {
            case "account":
                fassade_.updateBankAccount(id_, (String)params_[0], (Double)params_[1]);
                break;
            case "category":
                String categoryName = (String) params_[0];
                Category.CategoryType categoryType = (Category.CategoryType) params_[1];
                fassade_.updateCategory(id_, categoryName, categoryType);
                break;
            case "operation":
                fassade_.updateOperation(id_, (Operation.OperationType)params_[0], (Double) params_[1],
                        (LocalDate)params_[2], (Integer) params_[3], (Integer)params_[4]);
                break;
            default:
                throw new IllegalArgumentException("Неизвестный тип: " + type_);
        }
        System.out.println(type_ + " с ID " + id_ + " обновлен");
    }
}

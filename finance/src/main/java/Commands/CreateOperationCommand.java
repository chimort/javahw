package Commands;

import FinanceFassade.FinanceFassade;
import Entities.Operation;

import java.time.LocalDate;

public class CreateOperationCommand implements Command {
    private FinanceFassade fassade_;
    private int id_;
    private Operation.OperationType type_;
    private int bank_account_id_;
    private double amount_;
    private LocalDate date_;
    private int category_id_;

    public CreateOperationCommand(FinanceFassade fassade, int id, Operation.OperationType type,
                                  int bank_account_id, double amount, LocalDate date, int category_id)
    {
        this.fassade_ = fassade;
        this.id_ = id;
        this.type_ = type;
        this.bank_account_id_ = bank_account_id;
        this.amount_ = amount;
        this.date_ = date;
        this.category_id_ = category_id;
    }

    @Override
    public  void execute() {
        fassade_.createOperation(id_, type_, bank_account_id_, amount_, date_, category_id_);
        System.out.println("Операция с id " + id_ + " создана");
    }

}

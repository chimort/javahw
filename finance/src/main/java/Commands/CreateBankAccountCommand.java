package Commands;

import FinanceFassade.FinanceFassade;

public class CreateBankAccountCommand implements Command {
    private FinanceFassade fassade_;
    private int id_;
    private String name_;
    private double balance_;


    public CreateBankAccountCommand(FinanceFassade fassade, int id, String name, double balance) {
        this.fassade_ = fassade;
        this.name_ = name;
        this.id_ = id;
        this.balance_ = balance;
    }

    @Override
    public void execute() {
        fassade_.createBankAccount(id_, name_, balance_);
        System.out.println("Счет сосздан: " + name_);
    }
}

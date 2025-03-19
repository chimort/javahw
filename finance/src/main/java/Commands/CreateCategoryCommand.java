package Commands;

import Entities.Category;
import FinanceFassade.FinanceFassade;

public class CreateCategoryCommand implements Command {
    private FinanceFassade fassade_;
    private int id_;
    private Category.CategoryType type_;
    private String name_;

    public CreateCategoryCommand(FinanceFassade facade, int id, Category.CategoryType type, String name) {
        this.fassade_ = facade;
        this.id_ = id;
        this.type_ = type;
        this.name_ = name;
    }

    @Override
    public void execute() {
        fassade_.createCategory(id_, type_, name_);
        System.out.println("Категория создана: " + name_);
    }
}

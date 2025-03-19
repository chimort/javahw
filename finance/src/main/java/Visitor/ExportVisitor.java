package Visitor;

import Entities.BankAccount;
import Entities.Category;
import Entities.Operation;

import java.time.format.DateTimeFormatter;

public class ExportVisitor implements Visitor {
    protected final StringBuilder export_data_ = new StringBuilder();
    protected final DateTimeFormatter date_formatter_ = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @Override
    public void visit(BankAccount account) {
        export_data_.append("BankAccount,")
                .append(account.getId()).append(",")
                .append(account.getName()).append(",")
                .append(account.getBalance()).append(",")
                .append("null,null,null\n");
    }

    @Override
    public void visit(Category category) {
        export_data_.append("Category,")
                .append(category.getId()).append(",")
                .append(category.getName()).append(",")
                .append(category.getCategoryType()).append(",")
                .append("null,null,null\n");
    }

    @Override
    public void visit(Operation operation) {
        String formattedDate = operation.getDate().format(date_formatter_);
        export_data_.append(operation.getId()).append(",")
                .append(operation.getType()).append(",")
                .append(operation.getAmount()).append(",")
                .append(formattedDate).append(",")
                .append(operation.getBankAccount().getId()).append(",")
                .append(operation.getCategory().getId()).append("\n");
    }

    public String getExportData() {
        return export_data_.toString();
    }
}

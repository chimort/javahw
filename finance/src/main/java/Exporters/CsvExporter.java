package Exporters;

import Visitor.ExportVisitor;

import java.io.FileWriter;
import java.io.IOException;

public class CsvExporter extends ExportVisitor {
    private final String filename_;

    public CsvExporter(String filename) {
        this.filename_ = filename.endsWith(".csv") ? filename : filename + ".csv";
    }

    public void exportToFile() {
        try (FileWriter writer = new FileWriter(filename_)) {
            writer.append("id,type,amount,date,bankAccountId,categoryId\n");
            writer.write(getExportData());
            System.out.println("Данные успешно экспортированы в " + filename_);
        } catch (IOException e) {
            System.out.println("Ошибка при экспорте в CSV: " + e.getMessage());
        }
    }
}

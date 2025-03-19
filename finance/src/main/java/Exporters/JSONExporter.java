package Exporters;

import Visitor.ExportVisitor;

import java.io.FileWriter;
import java.io.IOException;

public class JSONExporter extends ExportVisitor {
    private final String filename_;

    public JSONExporter(String filename) {
        this.filename_ = filename.endsWith(".json") ? filename : filename + ".json";
    }

    @Override
    public String getExportData() {
        return "{\n" + export_data_.toString().replace("\n", ",\n") + "\n}";
    }

    public void exportToFile() {
        try (FileWriter writer = new FileWriter(filename_)) {
            writer.write(getExportData());
            System.out.println("Данные успешно экспортированы в " + filename_);
        } catch (IOException e) {
            System.out.println("Ошибка при экспорте в JSON: " + e.getMessage());
        }
    }
}

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
    public void exportToFile() {
        try (FileWriter writer = new FileWriter(filename_)) {
            writer.write("{\n" + getExportData().replace("\n", ",\n") + "\n}");
            System.out.println("Данные успешно экспортированы в " + filename_);
        } catch (IOException e) {
            System.out.println("Ошибка при экспорте в JSON: " + e.getMessage());
        }
    }
}

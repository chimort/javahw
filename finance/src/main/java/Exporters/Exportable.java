package Exporters;

import Visitor.ExportVisitor;

public interface Exportable {
    void accept(ExportVisitor visitor);
}

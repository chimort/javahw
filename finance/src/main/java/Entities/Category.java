package Entities;

import Visitor.ExportVisitor;


public class Category {
    public enum CategoryType {
        INCOME,
        CONSUMPTION
    }

    private final int id_;
    private final CategoryType type_;
    private String name_;

    public Category(int id, CategoryType type, String name) {
        this.id_ = id;
        this.type_ = type;
        this.name_ = name;
    }

    public int getId() { return id_; }
    public CategoryType getCategoryType() { return type_; }
    public String getName() { return name_; }

    public void setName(String name) { this.name_ = name; }

    public void accept(ExportVisitor visitor) {
        visitor.visit(this);
    }
}

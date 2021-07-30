package models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Inventory {
    private SimpleIntegerProperty id;
    private SimpleStringProperty name;
    private SimpleStringProperty specification;
    private SimpleStringProperty dueDate;

    public Inventory(
            Integer id,
            String name,
            String specification,
            String dueDate
    ) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.specification = new SimpleStringProperty(specification);
        this.dueDate = new SimpleStringProperty(dueDate);
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getSpecification() {
        return specification.get();
    }

    public SimpleStringProperty specificationProperty() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification.set(specification);
    }

    public String getDueDate() {
        return dueDate.get();
    }

    public SimpleStringProperty dueDateProperty() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate.set(dueDate);
    }
}

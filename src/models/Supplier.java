package models;

import database.DatabaseAccess;

public class Supplier extends User{
    private int totalPostOrders;
    private int totalQuotations;
    public Supplier(int id, String name, String username, String password, String role) {
        super(id, name, username, password, role);
        DatabaseAccess da = new DatabaseAccess();
        totalPostOrders = da.getSupplierTotalOrders();
        totalQuotations = da.getSupplierTotalQuotations();
    }

    public int getTotalPostOrders() {
        return totalPostOrders;
    }

    public void setTotalPostOrders(int totalPostOrders) {
        this.totalPostOrders = totalPostOrders;
    }

    public int getTotalQuotations() {
        return totalQuotations;
    }

    public void setTotalQuotations(int totalQuotations) {
        this.totalQuotations = totalQuotations;
    }
}

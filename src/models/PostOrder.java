package models;

public class PostOrder {
    int id;
    String name;
    String specification;
    int quantity;
    double unitPrice;
    String deliveryDue;
    String supportPeriod;
    int buyerId;
    int sellerId;

    public PostOrder(int id, String name, String specification, int quantity, double unitPrice, String deliveryDue, String supportPeriod, int buyerId, int sellerId) {
        this.id = id;
        this.name = name;
        this.specification = specification;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.deliveryDue = deliveryDue;
        this.supportPeriod = supportPeriod;
        this.buyerId = buyerId;
        this.sellerId = sellerId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getDeliveryDue() {
        return deliveryDue;
    }

    public void setDeliveryDue(String deliveryDue) {
        this.deliveryDue = deliveryDue;
    }

    public String getSupportPeriod() {
        return supportPeriod;
    }

    public void setSupportPeriod(String supportPeriod) {
        this.supportPeriod = supportPeriod;
    }

    public int getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(int buyerId) {
        this.buyerId = buyerId;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }
}

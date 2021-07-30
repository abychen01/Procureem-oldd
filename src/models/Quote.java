package models;

public class Quote {
    private int id ;
    private String name;
    private String specification ;
    private int quantity ;
    private double maxUnitPrice;
    private String deliveryDue;
    private String range;
    private String supportPeriod;
    private String additionalRequirements;
    private int buyerId;
    private int sellerId;

    public Quote(int id,
                 String name,
                 String specification,
                 int quantity,
                 double maxUnitPrice,
                 String deliveryDue,
                 String range,
                 String supportPeriod,
                 String additionalRequirements,
                 int buyerId,
                 int sellerId
    ) {
        this.id = id;
        this.name = name;
        this.specification = specification;
        this.quantity = quantity;
        this.maxUnitPrice = maxUnitPrice;
        this.deliveryDue = deliveryDue;
        this.range = range;
        this.supportPeriod = supportPeriod;
        this.additionalRequirements = additionalRequirements;
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

    public double getMaxUnitPrice() {
        return maxUnitPrice;
    }

    public void setMaxUnitPrice(double maxUnitPrice) {
        this.maxUnitPrice = maxUnitPrice;
    }

    public String getDeliveryDue() {
        return deliveryDue;
    }

    public void setDeliveryDue(String deliveryDue) {
        this.deliveryDue = deliveryDue;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getSupportPeriod() {
        return supportPeriod;
    }

    public void setSupportPeriod(String supportPeriod) {
        this.supportPeriod = supportPeriod;
    }

    public String getAdditionalRequirements() {
        return additionalRequirements;
    }

    public void setAdditionalRequirements(String additionalRequirements) {
        this.additionalRequirements = additionalRequirements;
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

package com.ra.model.entity;

public class Product {
    private int productID;
    private String productName;
    private double price;
    private Category category;

    public Product() {
    }

    public Product(int productID, String productName, double price, Category category) {
        this.productID = productID;
        this.productName = productName;
        this.price = price;
        this.category = category;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}

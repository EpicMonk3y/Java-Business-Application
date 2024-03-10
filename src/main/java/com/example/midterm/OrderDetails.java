package com.example.midterm;

import java.util.Date;

public class OrderDetails {
    private Date orderDate;
    private String product;
    private int quantity;
    private double unitPrice;

    public OrderDetails(Date orderDate, String product, int quantity, double unitPrice) {
        this.orderDate = orderDate;
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
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
}

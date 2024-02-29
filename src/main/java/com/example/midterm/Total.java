package com.example.midterm;

public class Total{
    private int orderId;
    private double total;

    public Total(int orderId, double total){
        this.orderId = orderId;
        this.total = total;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}

package com.example.midterm;

public class CustomerInfo {
    private String customerName;
    private String city;

    public CustomerInfo(String customerName, String city) {
        this.customerName = customerName;
        this.city = city;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}

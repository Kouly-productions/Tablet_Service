package com.example.tabletservice;

public class TabletData {
    public enum Brand {
        SAMSUNG,
        ACER,
        APPLE,
        MICROSOFT
    }
    public enum Cable {
        USB_C,
        LIGHTNING,
        MICRO_USB,
    }
    public Brand brand;
    public Cable cable;
    public String userName;
    public String userPhone;
    public String userEmail;
    public String time;

    public TabletData(Brand brand, Cable cable, String userName, String userPhone,
                      String userEmail, String time)
    {
        this.brand = brand;
        this.cable = cable;
        this.userName = userName;
        this.userPhone = userPhone;
        this.userEmail = userEmail;
        this.time = time;
    }

    public Brand getBrand() {
        return brand;
    }

    public Cable getCable() {
        return cable;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getTime() {
        return time;
    }
}

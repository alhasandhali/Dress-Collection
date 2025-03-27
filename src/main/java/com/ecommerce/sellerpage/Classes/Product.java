package com.ecommerce.sellerpage.Classes;

public class Product {
    private int dressId;
    private String dressName;
    private String dressType;
    private String dressSize;
    private String dressColor;
    private double dressPrice;
    private String dressDetails;
    private String purchaseDate;
    private int dressQuantity;
    private String discountCode;
    private String customerGender;
    private double totalPrice;
    private boolean checkBox;

    //-----------------------------------------Constructor----------------------------------------------------
    public Product() {
    }



    public Product(int dressId, String discountCode) {
        this.dressId = dressId;
        this.discountCode = discountCode;
    }

    public Product(int dressId, String dressName, String dressType, String dressColor, double dressPrice, String purchaseDate, int dressQuantity, boolean checkBox) {
        this.dressId = dressId;
        this.dressName = dressName;
        this.dressType = dressType;
        this.dressColor = dressColor;
        this.dressPrice = dressPrice;
        this.purchaseDate = purchaseDate;
        this.dressQuantity = dressQuantity;
        this.checkBox = checkBox;
    }

    public Product(int dressId, String dressName, String dressType, String dressSize, String dressColor, double dressPrice, String dressDetails, String purchaseDate, int dressQuantity, String discountCode, String customerGender, boolean checkBox) {
        this.dressId = dressId;
        this.dressName = dressName;
        this.dressType = dressType;
        this.dressSize = dressSize;
        this.dressColor = dressColor;
        this.dressPrice = dressPrice;
        this.dressDetails = dressDetails;
        this.purchaseDate = purchaseDate;
        this.dressQuantity = dressQuantity;
        this.discountCode = discountCode;
        this.customerGender = customerGender;
        this.checkBox = checkBox;
    }

    public Product(String dressName, double dressPrice, int dressQuantity) {
        this.dressName = dressName;
        this.dressPrice = dressPrice;
        this.dressQuantity = dressQuantity;
    }

    public Product(int dressId, String dressName, String dressSize, String dressColor, double dressPrice, int dressQuantity) {
        this.dressId = dressId;
        this.dressName = dressName;
        this.dressSize = dressSize;
        this.dressColor = dressColor;
        this.dressPrice = dressPrice;
        this.dressQuantity = dressQuantity;
    }

    public Product(int dressId, String dressName, String dressType, String dressSize, String dressColor, double dressPrice, String dressDetails, int dressQuantity) {
        this.dressId = dressId;
        this.dressName = dressName;
        this.dressType = dressType;
        this.dressSize = dressSize;
        this.dressColor = dressColor;
        this.dressPrice = dressPrice;
        this.dressDetails = dressDetails;
        this.dressQuantity = dressQuantity;
    }

    public Product(int dressId, String dressName, String dressType, String dressSize, String dressColor, double dressPrice, int dressQuantity) {
        this.dressId = dressId;
        this.dressName = dressName;
        this.dressType = dressType;
        this.dressSize = dressSize;
        this.dressColor = dressColor;
        this.dressPrice = dressPrice;
        this.dressQuantity = dressQuantity;
    }

    public Product(int dressId, String dressName, String dressSize, String dressColor, double dressPrice, int dressQuantity, double totalPrice) {
        this.dressId = dressId;
        this.dressName = dressName;
        this.dressSize = dressSize;
        this.dressColor = dressColor;
        this.dressPrice = dressPrice;
        this.dressQuantity = dressQuantity;
        this.totalPrice = totalPrice;
    }

    //------------------Getter & Setter------------------------------------------------

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getDressName() {
        return dressName;
    }

    public void setDressName(String dressName) {
        this.dressName = dressName;
    }

    public String getDressType() {
        return dressType;
    }

    public void setDressType(String dressType) {
        this.dressType = dressType;
    }

    public String getDressSize() {
        return dressSize;
    }

    public void setDressSize(String dressSize) {
        this.dressSize = dressSize;
    }

    public String getDressColor() {
        return dressColor;
    }

    public void setDressColor(String dressColor) {
        this.dressColor = dressColor;
    }

    public double getDressPrice() {
        return dressPrice;
    }

    public void setDressPrice(double dressPrice) {
        this.dressPrice = dressPrice;
    }

    public String getDressDetails() {
        return dressDetails;
    }

    public void setDressDetails(String dressDetails) {
        this.dressDetails = dressDetails;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public int getDressQuantity() {
        return dressQuantity;
    }

    public void setDressQuantity(int dressQuantity) {
        this.dressQuantity = dressQuantity;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public String getCustomerGender() {
        return customerGender;
    }

    public void setCustomerGender(String customerGender) {
        this.customerGender = customerGender;
    }

    public boolean isCheckBox() {
        return checkBox;
    }

    public void setCheckBox(boolean checkBox) {
        this.checkBox = checkBox;
    }

    public int getDressId() {
        return dressId;
    }

    public void setDressId(int dressId) {
        this.dressId = dressId;
    }
}

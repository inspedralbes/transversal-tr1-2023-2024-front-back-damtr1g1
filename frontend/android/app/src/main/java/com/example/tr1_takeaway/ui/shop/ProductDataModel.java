package com.example.tr1_takeaway.ui.shop;

public class ProductDataModel {

    private String productImageSource;
    private String productName;
    private String productDescription;
    private double productPrice;
    private int productCategoryId;
    private int productQuantity;

    public void setProductImageSource(String productImageSource) {
        this.productImageSource = productImageSource;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public void setProductCategoryId(int productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }
}

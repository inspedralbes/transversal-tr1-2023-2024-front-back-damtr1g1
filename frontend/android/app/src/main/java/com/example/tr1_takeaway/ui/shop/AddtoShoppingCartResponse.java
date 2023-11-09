package com.example.tr1_takeaway.ui.shop;

import com.google.gson.annotations.SerializedName;

public class AddtoShoppingCartResponse {
    @SerializedName("message")
    private String message;

    public String getMessage() {
        return message;
    }
}

package com.grocery.payload.request.user_request;

import javax.validation.constraints.NotBlank;

public class AddToCartRequest {

    private String quantity;

    public AddToCartRequest() {
    }

    public AddToCartRequest(String quantity) {
        this.quantity = quantity;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}

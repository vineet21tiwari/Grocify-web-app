package com.grocery.payload.response.user_response;

import com.grocery.models.Product;

import java.util.List;

public class ShowItemDetailsResponse {
    private List<Product> product;

    public ShowItemDetailsResponse(List<Product> product) {
        this.product = product;
    }

    public List<Product> getProduct() {
        return product;
    }

    public void setProduct(List<Product> product) {
        this.product = product;
    }
}

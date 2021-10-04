package com.grocery.payload.response.user_response;

import com.grocery.models.Cart;

import java.util.List;

public class ShowCartItemsResponse {
    private List<Cart> cart;
    private Long totalprice;

    public ShowCartItemsResponse(List<Cart> cart, Long totalprice) {
        this.cart = cart;
        this.totalprice = totalprice;
    }

    public List<Cart> getCart() {
        return cart;
    }

    public void setCart(List<Cart> cart) {
        this.cart = cart;
    }

    public Long getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(Long totalprice) {
        this.totalprice = totalprice;
    }
}

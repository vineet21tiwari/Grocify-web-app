package com.grocery.spring.security.jwt.payload.request.user_request;

import javax.validation.constraints.NotBlank;

public class AddItemRequest {
    @NotBlank
    private String shopname;

    @NotBlank
    private String itemname;

    @NotBlank
    private String itemprice;

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getItemprice() {
        return itemprice;
    }

    public void setItemprice(String itemprice) {
        this.itemprice = itemprice;
    }
}

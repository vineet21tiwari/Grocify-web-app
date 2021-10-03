package com.grocery.spring.security.jwt.payload.request.user_request;

import javax.validation.constraints.NotBlank;

public class SellerSignupAddItem {
    private Long id;
    @NotBlank
    private String shopname;

    @NotBlank
    private String itemname;

    @NotBlank
    private String itemprice;

    public SellerSignupAddItem(Long id, String shopname, String itemname, String itemprice) {
        this.id = id;
        this.shopname = shopname;
        this.itemname = itemname;
        this.itemprice = itemprice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

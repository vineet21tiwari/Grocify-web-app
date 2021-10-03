package com.grocery.spring.security.jwt.payload.request.user_request;

public class EditItemDetailsRequest {
    private String itemname;
    private String itemprice;



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

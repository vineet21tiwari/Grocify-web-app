package com.grocery.spring.security.jwt.payload.response.user_response;

public class ShopsResponse {
    private Long sellerid;
    private String shopname;
    private String mobile;
    private String first_name;
    private String last_name;

    public ShopsResponse(Long sellerid, String shopname, String mobile, String first_name, String last_name) {
        this.sellerid = sellerid;
        this.shopname = shopname;
        this.mobile = mobile;
        this.first_name = first_name;
        this.last_name = last_name;
    }

    public ShopsResponse() {

    }

    public Long getSellerid() {
        return sellerid;
    }

    public void setSellerid(Long sellerid) {
        this.sellerid = sellerid;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
}

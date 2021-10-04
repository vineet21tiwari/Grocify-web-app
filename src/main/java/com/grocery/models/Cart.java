package com.grocery.models;

import javax.persistence.*;

@Entity
@Table(	name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private  String shopname;
    private String itemname;
    private String itemprice;
    private Long userid;

    public Cart() {

    }

    public Cart(String shopname, String itemname, String itemprice, Long userid) {
        this.shopname = shopname;
        this.itemname = itemname;
        this.itemprice = itemprice;
        this.userid = userid;
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

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }
}

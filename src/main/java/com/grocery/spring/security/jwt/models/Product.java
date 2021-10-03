package com.grocery.spring.security.jwt.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(	name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 20)
    @Column(name = "shopname")
    private String shopname;

    @NotBlank
    @Size(max = 50)
    @Column(name = "itemname")
    private String itemname;

    @NotBlank
    @Size(max = 120)
    @Column(name = "itemprice")
    private String itemprice;

    private Long userid;


    public Product(){

    }

    public Product(String shopname, String itemname, String itemprice, Long userid) {
        this.shopname = shopname;
        this.itemname = itemname;
        this.itemprice = itemprice;
        this.userid=userid;

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

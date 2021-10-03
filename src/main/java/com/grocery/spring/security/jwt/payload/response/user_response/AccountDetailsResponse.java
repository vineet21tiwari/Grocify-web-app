package com.grocery.spring.security.jwt.payload.response.user_response;

import java.util.List;

public class AccountDetailsResponse {
    private Long id;
    private String username;
    private String email;
    private List<String> roles;
    private String firstname;
    private String lastname;
    private String address;
    private String mobile;
    private String active;
    private String account_status;

    public AccountDetailsResponse(Long id,String username, String firstname,String lastname,
                                  String email, String mobile, String address,
                                  String account_status,String active,List<String> roles) {
        this.id=id;
        this.username = username;
        this.firstname=firstname;
        this.lastname=lastname;
        this.email=email;
        this.mobile=mobile;
        this.address=address;
        this.account_status=account_status;
        this.active=active;
        this.roles = roles;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getAccount_status() {
        return account_status;
    }

    public void setAccount_status(String account_status) {
        this.account_status = account_status;
    }
}

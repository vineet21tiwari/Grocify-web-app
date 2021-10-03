package com.grocery.spring.security.jwt.payload.response.user_response;

public class SellerSignupResponse {
    private Long id;
    private String message;

    public SellerSignupResponse(Long id, String message) {
        this.id = id;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

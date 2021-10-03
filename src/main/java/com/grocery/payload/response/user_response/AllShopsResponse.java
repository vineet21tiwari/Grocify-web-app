package com.grocery.payload.response.user_response;

import java.util.List;

public class AllShopsResponse {
    List<ShopsResponse> shopsResponse;

    public AllShopsResponse(List<ShopsResponse> shopsResponse) {
        this.shopsResponse = shopsResponse;
    }

    public List<ShopsResponse> getShopsResponse() {
        return shopsResponse;
    }

    public void setShopsResponse(List<ShopsResponse> shopsResponse) {
        this.shopsResponse = shopsResponse;
    }
}

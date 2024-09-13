package com.ecom.pincode;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class PostalResponse
{
    @JsonProperty("Message")
    private String message;
    @JsonProperty("Status")
    private String status;
    @JsonProperty("PostOffice")
    private List<PostOffice> postOffice;
}

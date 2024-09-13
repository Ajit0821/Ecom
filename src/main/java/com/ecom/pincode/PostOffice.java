package com.ecom.pincode;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PostOffice {
    @JsonProperty("Name")
    private String name;

    @JsonProperty("Description")
    private String description;
    @JsonProperty("BranchType")
    private String branchType;
    @JsonProperty("DeliveryStatus")
    private String deliveryStatus;
    @JsonProperty("Circle")
    private String circle;
    @JsonProperty("Region")
    private String region;
    @JsonProperty("District")
    private String district;

    @JsonProperty("Division")
    private String division;
    @JsonProperty("Block")
    private String block;
    @JsonProperty("State")
    private String state;
    @JsonProperty("Country")
    private String country;
    @JsonProperty("Pincode")
    private String pincode;
}

package com.burgers.app.Request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class OrderRequest {
    @NotBlank
    private String name;

    @NotBlank
    private String Street;

    @NotBlank
    private String city;

    @NotBlank
    private String state;
 
    @NotBlank
    private String zip;

    @NotNull
    private boolean freeDelivery;
}

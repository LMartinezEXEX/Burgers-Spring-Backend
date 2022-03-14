package com.burgers.app.Request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class IngredientRequest {
    
    @NotBlank
    private String name;

    @NotNull
    private Long price;

    @NotNull
    private String description;

    @NotBlank
    private String type;
}

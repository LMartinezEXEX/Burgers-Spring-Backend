package com.burgers.app.DTO.Request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class IngredientDTO {
    @NotBlank
    private String name;

    @NotNull
    private Long price;

    @NotNull
    private String description;

    @NotBlank
    private String type;
}
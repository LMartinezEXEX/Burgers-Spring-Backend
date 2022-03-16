package com.burgers.app.DTO.Request;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class DeleteIngredientDTO {
    @NotNull
    private Long ingredientId;
}

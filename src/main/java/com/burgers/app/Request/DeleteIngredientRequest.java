package com.burgers.app.Request;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class DeleteIngredientRequest {
    @NotNull
    private Long ingredientId;
}

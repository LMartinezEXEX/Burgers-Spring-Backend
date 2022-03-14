package com.burgers.app.Request;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class RemoveIngredientRequest {

    @NotNull
    private Long burgerInOrderId;

    @NotNull
    private Long ingredientId;
}

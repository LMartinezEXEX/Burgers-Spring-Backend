package com.burgers.app.DTO.Request;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class DeleteBurgerIngredientDTO {
    @NotNull
    private Long burgerInOrderId;

    @NotNull
    private Long ingredientId;
}

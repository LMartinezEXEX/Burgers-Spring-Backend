package com.burgers.app.DTO.Request;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class DeleteBurgerDTO {
    @NotNull
    private Long burgerInOrderId;
}

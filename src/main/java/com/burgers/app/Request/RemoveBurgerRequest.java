package com.burgers.app.Request;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class RemoveBurgerRequest {
    
    @NotNull
    private Long burgerInOrderId;
}

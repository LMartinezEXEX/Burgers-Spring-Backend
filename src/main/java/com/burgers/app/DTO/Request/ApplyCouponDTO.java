package com.burgers.app.DTO.Request;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class ApplyCouponDTO {
    @NotBlank
    private String code;
}

package com.burgers.app.DTO.Request;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import lombok.Data;

@Data
public class CouponDTO {
    @NotNull
    @Range(min = 0)
    private Long discount;

    @NotNull
    private LocalDateTime availableUntil;
}

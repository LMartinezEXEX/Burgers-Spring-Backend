package com.burgers.app.Request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ModifyRoleRequest {

    @NotNull
    private Long userId;

    @NotBlank
    private String role;
}

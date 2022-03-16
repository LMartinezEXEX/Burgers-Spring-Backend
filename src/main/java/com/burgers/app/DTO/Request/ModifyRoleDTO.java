package com.burgers.app.DTO.Request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ModifyRoleDTO {
    @NotNull
    private Long userId;

    @NotBlank
    private String role;
}

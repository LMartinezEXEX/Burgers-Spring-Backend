package com.burgers.app.DTO.Request;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class BurgerDTO {
    @NotBlank
    @Size(min = 3, max = 15)
    private String name;

    @NotNull
    private String size;

    @Size(min = 1)
    private List<String> ingredients;
}

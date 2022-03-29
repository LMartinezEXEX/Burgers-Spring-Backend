package com.burgers.app.DTO.Response;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.burgers.app.Domain.Burger;

import lombok.Data;

@Data
public class OrderToDTO {

    @NotNull
    private Long id;

    @NotBlank
    private String street;

    @NotBlank
    private String city;

    @NotBlank
    private String state;

    @NotBlank
    private String zip;

    @NotNull
    private List<Burger> burgers;

    public OrderToDTO(Long id, String street, String city, String state, String zip, List<Burger> burgers) {
        this.id = id;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.burgers = burgers;
    }
}

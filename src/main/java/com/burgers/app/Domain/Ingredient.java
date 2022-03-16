package com.burgers.app.Domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import lombok.Data;

@Data
@Entity
@Table(name = "ingredients",
        uniqueConstraints = @UniqueConstraint(columnNames = {"name"})
        )
public class Ingredient {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @NotNull(message = "Ingredient name cant be null")
    private String name;

    @NotNull
    @Range(min = 0)
    private Long price;

    private String description;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Ingredient type cant be null")
    private IngredientType type;


    public static enum IngredientType {
        SAUCE, PROTEIN, TOPPING, EXTRA
    }

    public Ingredient() {
    }

    public Ingredient(String name, Long price, String description, IngredientType type) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.type = type;
    }
}

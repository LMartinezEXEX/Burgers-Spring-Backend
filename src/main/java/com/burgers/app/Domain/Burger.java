package com.burgers.app.Domain;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.burgers.app.Domain.Ingredient.IngredientType;

import org.hibernate.validator.constraints.Range;

import lombok.Data;

@Data
@Entity
public class Burger {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Transient
    private Long burgerInOrderId;

    private Date createdAt;

    @NotNull
    @Size(min = 5, message = "Name must be at least 5 characters long")
    private String name;

    @NotNull
    @Range(min = 0)
    private Long price = 0L;

    @ManyToMany(targetEntity=Ingredient.class)
    @Size(min = 1, message = "You must choose at least 1 ingredient")
    private List<Ingredient> ingredients;

    @OneToOne(targetEntity = BurgerSize.class)
    private BurgerSize size;

    @PrePersist
    public void createdAt(){
        this.createdAt = new Date();
    }

    public boolean ingredientExistsById(Long id){
        for(Ingredient ingredient : ingredients) {
            if (ingredient.getId().equals(id))
                return true;
        }
        return false;
    }

    public Burger(){
        
    }

    public Burger(String name, BurgerSize size, List<Ingredient> ingredients) {
        this.name = name;
        this.size = size;
        this.ingredients = ingredients;
        
        this.price += size.getPrice();
        for(Ingredient i : ingredients) {
            this.price += i.getPrice();
        }
    }

    public boolean isValid() {
        boolean hasProtein = false;
        boolean isValid = false;

        if(this.ingredients.isEmpty() || Objects.isNull(size)) return false;

        for(Ingredient ingredient : this.ingredients){
            if(ingredient.getType()==IngredientType.PROTEIN && !hasProtein)
                isValid = true;
            else if (ingredient.getType()==IngredientType.PROTEIN && hasProtein){
                isValid = false;
            }
        }

        return isValid;
    }
}

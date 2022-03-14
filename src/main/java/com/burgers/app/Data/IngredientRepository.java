package com.burgers.app.Data;

import com.burgers.app.Domain.Ingredient;

import org.springframework.data.repository.CrudRepository;

public interface IngredientRepository extends CrudRepository<Ingredient, Long>{
    
    Ingredient findByName(String name);

    boolean existsByName(String name);
    
}

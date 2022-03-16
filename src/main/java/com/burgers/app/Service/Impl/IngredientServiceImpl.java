package com.burgers.app.Service.Impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.burgers.app.Data.IngredientRepository;
import com.burgers.app.Domain.Ingredient;
import com.burgers.app.Domain.Ingredient.IngredientType;
import com.burgers.app.Exception.BurgersException;
import com.burgers.app.Service.IngredientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IngredientServiceImpl implements IngredientService {

    @Autowired
    private IngredientRepository ingredientRepository;

    @Override
    public Ingredient add(Ingredient ingredient) throws BurgersException {
        
        if(ingredientRepository.existsByName(ingredient.getName())) 
            throw new BurgersException("Error: Ingredient name alredy exists");

        ingredientRepository.save(ingredient);

        return ingredient;
    }

    @Override
    public Ingredient delete(Ingredient ingredient) {

        ingredientRepository.deleteById(ingredient.getId());

        return ingredient;
    }

    @Override
    public List<Ingredient> getAll() {
        
        List<Ingredient> ingredients = new ArrayList<>();

        ingredientRepository.findAll().forEach(ingredients::add);

        return ingredients;
    }

    @Override
    public List<IngredientType> getAllTypes() {
        
        List<IngredientType> types = Arrays.asList(IngredientType.values());

        return types;
    }
}

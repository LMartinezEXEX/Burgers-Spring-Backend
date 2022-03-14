package com.burgers.app.Service;

import java.util.List;

import com.burgers.app.Domain.Ingredient;
import com.burgers.app.Domain.Ingredient.IngredientType;
import com.burgers.app.Exception.BurgersException;
import com.burgers.app.Request.DeleteIngredientRequest;
import com.burgers.app.Request.IngredientRequest;

public interface IngredientService {
    
    public Ingredient add(Ingredient ingredient) throws BurgersException;

    public Ingredient delete(Ingredient ingredient);

    public List<Ingredient> getAll();

    public List<IngredientType> getAllTypes();

    public Ingredient requestToEntity(IngredientRequest iRequest);

    public Ingredient requestToEntity(DeleteIngredientRequest dIngredientRequest);
}

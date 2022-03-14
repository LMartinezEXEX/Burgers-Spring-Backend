package com.burgers.app.Service;

import java.util.Map;

import com.burgers.app.Domain.Burger;
import com.burgers.app.Domain.Ingredient;
import com.burgers.app.Domain.Order;
import com.burgers.app.Exception.BurgersException;
import com.burgers.app.Request.BurgerRequest;
import com.burgers.app.Request.DeleteIngredientRequest;
import com.burgers.app.Request.IngredientRequest;
import com.burgers.app.Request.ModifyRoleRequest;
import com.burgers.app.Request.OrderRequest;
import com.burgers.app.Request.RemoveBurgerRequest;
import com.burgers.app.Request.RemoveIngredientRequest;

public interface MapperService {
    
    public Order toEntity(OrderRequest oRequest);

    public Burger toEntity(BurgerRequest bRequest);

    public Burger toEntity(RemoveBurgerRequest rBurgerRequest);

    public Ingredient toEntity(IngredientRequest iRequest) throws BurgersException;
    
    public Ingredient toEntity(DeleteIngredientRequest dIngredientRequest) throws BurgersException;
    
    public Map<String, Object> toEntity(ModifyRoleRequest mRoleRequest) throws BurgersException;
    
    public Map<String, Object> toEntity(RemoveIngredientRequest rIngredientRequest) throws BurgersException;
}

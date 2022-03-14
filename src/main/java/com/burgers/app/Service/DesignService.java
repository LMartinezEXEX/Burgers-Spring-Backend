package com.burgers.app.Service;

import java.util.Map;

import javax.servlet.http.HttpSession;

import com.burgers.app.Domain.Burger;
import com.burgers.app.Domain.Ingredient;
import com.burgers.app.Exception.BurgersException;
import com.burgers.app.Request.BurgerRequest;
import com.burgers.app.Request.RemoveBurgerRequest;
import com.burgers.app.Request.RemoveIngredientRequest;

public interface DesignService {
    
    public Burger addBurger(Burger burger, HttpSession session) throws BurgersException;

    public Burger removeBurger(Burger burger, HttpSession session) throws BurgersException;

    public Ingredient removeIngredientFromBurger(Burger burger, Ingredient ingredient, HttpSession session) throws BurgersException;

    public Burger RequestToEntity(BurgerRequest bRequest);

    public Burger RequestToEntity(RemoveBurgerRequest rBurgerRequest);

    public Map<String, Object> RequestToEntity(RemoveIngredientRequest rIngredientRequest);
}

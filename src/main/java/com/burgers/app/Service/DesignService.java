package com.burgers.app.Service;

import javax.servlet.http.HttpSession;

import com.burgers.app.Domain.Burger;
import com.burgers.app.Domain.Ingredient;
import com.burgers.app.Exception.BurgersException;

public interface DesignService {
    
    public Burger addBurger(Burger burger, HttpSession session) throws BurgersException;

    public Burger removeBurger(Burger burger, HttpSession session) throws BurgersException;

    public Ingredient removeIngredientFromBurger(Burger burger, Ingredient ingredient, HttpSession session) throws BurgersException;
}

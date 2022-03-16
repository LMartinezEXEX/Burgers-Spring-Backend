package com.burgers.app.Service.Impl;

import java.util.Objects;

import javax.servlet.http.HttpSession;

import com.burgers.app.Domain.Burger;
import com.burgers.app.Domain.Ingredient;
import com.burgers.app.Domain.Order;
import com.burgers.app.Domain.Ingredient.IngredientType;
import com.burgers.app.Exception.BurgersException;
import com.burgers.app.Service.DesignService;

import org.springframework.stereotype.Service;

@Service
public class DesignServiceImpl implements DesignService {

    @Override
    public Burger addBurger(Burger burger, HttpSession session) throws BurgersException {
        
        if(!burger.isValid()) throw new BurgersException("Error: Invalid Burger");

        Order order = (Order) session.getAttribute("userOrder");
        if(Objects.isNull(order)) {
            order = new Order();
            session.setAttribute("userOrder", order);
        }

        order.addDesign(burger);

        return burger;
    }

    @Override
    public Burger removeBurger(Burger burger, HttpSession session) throws BurgersException {
        
        Order order = (Order) session.getAttribute("userOrder");
        if(Objects.isNull(order)) throw new BurgersException("Error: No order found");

        Burger burgerToRemove = order.getBurgerByBurgerInOrderId(burger.getBurgerInOrderId());
        if(Objects.isNull(burgerToRemove)) throw new BurgersException("Error: Burger not found in order");

        order.getBurgers().remove(burgerToRemove);

        return burgerToRemove;
    }

    @Override
    public Ingredient removeIngredientFromBurger(Burger burger, Ingredient ingredient, HttpSession session) throws BurgersException {
        
        Order order = (Order) session.getAttribute("userOrder");
        if(Objects.isNull(order)) throw new BurgersException("Error: No order found");

        if(Objects.isNull(ingredient)) throw new BurgersException("Error: ingredient not found");

        if(ingredient.getType()==IngredientType.PROTEIN) throw new BurgersException("Error: Can not remove the protein");

        Burger burgerToModify = order.getBurgerByBurgerInOrderId(burger.getBurgerInOrderId());

        if(!burgerToModify.ingredientExistsById(ingredient.getId())) throw new BurgersException("Error: Ingredient not in burger");
        
        burgerToModify.getIngredients().remove(ingredient);
        burgerToModify.setPrice(burgerToModify.getPrice() - ingredient.getPrice());

        return ingredient;
    }
}

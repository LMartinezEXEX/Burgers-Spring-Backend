package com.burgers.app.Service;

import java.util.Map;

import com.burgers.app.DTO.Request.BurgerDTO;
import com.burgers.app.DTO.Request.DeleteBurgerDTO;
import com.burgers.app.DTO.Request.DeleteBurgerIngredientDTO;
import com.burgers.app.DTO.Request.DeleteIngredientDTO;
import com.burgers.app.DTO.Request.IngredientDTO;
import com.burgers.app.DTO.Request.ModifyRoleDTO;
import com.burgers.app.DTO.Request.OrderDTO;
import com.burgers.app.DTO.Response.OrderToDTO;
import com.burgers.app.Domain.Burger;
import com.burgers.app.Domain.Ingredient;
import com.burgers.app.Domain.Order;
import com.burgers.app.Exception.BurgersException;

public interface MapperService {
    
    public Order toEntity(OrderDTO oRequest);

    public Burger toEntity(BurgerDTO bRequest);

    public Burger toEntity(DeleteBurgerDTO rBurgerRequest);

    public Ingredient toEntity(IngredientDTO iRequest) throws BurgersException;
    
    public Ingredient toEntity(DeleteIngredientDTO dIngredientRequest) throws BurgersException;
    
    public Map<String, Object> toEntity(ModifyRoleDTO mRoleRequest) throws BurgersException;
    
    public Map<String, Object> toEntity(DeleteBurgerIngredientDTO rIngredientRequest) throws BurgersException;

    public OrderToDTO toDTO(Order order);
}

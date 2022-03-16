package com.burgers.app.Service.Impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import com.burgers.app.DTO.Request.BurgerDTO;
import com.burgers.app.DTO.Request.DeleteBurgerDTO;
import com.burgers.app.DTO.Request.DeleteBurgerIngredientDTO;
import com.burgers.app.DTO.Request.DeleteIngredientDTO;
import com.burgers.app.DTO.Request.IngredientDTO;
import com.burgers.app.DTO.Request.ModifyRoleDTO;
import com.burgers.app.DTO.Request.OrderDTO;
import com.burgers.app.Data.BurgerSizeRepository;
import com.burgers.app.Data.IngredientRepository;
import com.burgers.app.Data.RoleRepository;
import com.burgers.app.Data.UserRepository;
import com.burgers.app.Domain.Burger;
import com.burgers.app.Domain.BurgerSize;
import com.burgers.app.Domain.ERole;
import com.burgers.app.Domain.Ingredient;
import com.burgers.app.Domain.Order;
import com.burgers.app.Domain.Role;
import com.burgers.app.Domain.User;
import com.burgers.app.Domain.Ingredient.IngredientType;
import com.burgers.app.Exception.BurgersException;
import com.burgers.app.Service.MapperService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MapperServiceImpl implements MapperService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private BurgerSizeRepository burgerSizeRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Burger toEntity(BurgerDTO bRequest) {

        List<Ingredient> ingredients = new ArrayList<>();

        bRequest.getIngredients().stream()
        .forEach(ingredientName -> {
            Ingredient ingredient = ingredientRepository.findByName(ingredientName);
            if(!Objects.isNull(ingredient)) 
                ingredients.add(ingredient);

            else throw new RuntimeException("Error: Ingredient is not found.");
        });

        BurgerSize size = burgerSizeRepository.findByName(bRequest.getSize());

        Burger burger = new Burger(bRequest.getName(), size, ingredients);

        return burger;
    }

    @Override
    public Burger toEntity(DeleteBurgerDTO rBurgerRequest) {

        Burger burger = new Burger();
        burger.setBurgerInOrderId(rBurgerRequest.getBurgerInOrderId());

        return burger;
    }

    @Override
    public Ingredient toEntity(IngredientDTO iRequest) throws BurgersException {

        IngredientType type;
        try {
            type = IngredientType.valueOf(iRequest.getType());
        } catch (IllegalArgumentException iae) {
            throw new BurgersException("Error: Invalid ingredient type");
        }

        Ingredient ingredient = new Ingredient( iRequest.getName().toUpperCase(), 
                                                iRequest.getPrice(), 
                                                iRequest.getDescription(), 
                                                type
                                                );

        return ingredient;
    }

    @Override
    public Ingredient toEntity(DeleteIngredientDTO dIngredientRequest) throws BurgersException {

        Optional<Ingredient> optIngredient = ingredientRepository.findById(dIngredientRequest.getIngredientId());
        if(optIngredient.isEmpty()) throw new BurgersException("Error: Ingredient not found");

        Ingredient ingredient = optIngredient.get();

        return ingredient;
    }

    @Override
    public Order toEntity(OrderDTO oRequest) {

        Order orderData = new Order(oRequest.getName(), oRequest.getStreet(), oRequest.getCity(), oRequest.getState(), oRequest.getZip());
        orderData.setUsingFreeDelivery(oRequest.isFreeDelivery());
        
        return orderData;
    }

    @Override
    public Map<String, Object> toEntity(DeleteBurgerIngredientDTO rIngredientRequest) throws BurgersException {

        Map<String, Object> map = new HashMap<>();

        Optional<Ingredient> optIngredient = ingredientRepository.findById(rIngredientRequest.getIngredientId());
        Ingredient ingredient;
        if(optIngredient.isEmpty()) throw new BurgersException("Error: Ingredient not found");
        ingredient = optIngredient.get();

        Burger burger = new Burger();
        burger.setBurgerInOrderId(rIngredientRequest.getBurgerInOrderId());

        map.put("Burger", burger);
        map.put("Ingredient", ingredient);

        return map;
    }

    @Override
    public Map<String, Object> toEntity(ModifyRoleDTO mRoleRequest) throws BurgersException {

        Map<String, Object> map = new HashMap<>();
        User user;
        Role role;

        switch(mRoleRequest.getRole()) {
            
            case "admin":
                role = roleRepository.findByName(ERole.ROLE_ADMIN);
                if(Objects.isNull(role)) throw new RuntimeException("Admin role not found!");
                break;

            case "mod":
                role = roleRepository.findByName(ERole.ROLE_MODERATOR);
                if(Objects.isNull(role)) throw new RuntimeException("Moderator role not found!");
                break;
            
            default:
                throw new BurgersException("Error: Unknown role");
        }

        Optional<User> optUser = userRepository.findById(mRoleRequest.getUserId());
        if(optUser.isEmpty()) throw new BurgersException("Error: User not found");

        user = optUser.get();

        map.put("User", user);
        map.put("Role", role);

        return map;
    }
    
}

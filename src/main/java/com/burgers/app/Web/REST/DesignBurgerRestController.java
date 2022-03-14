package com.burgers.app.Web.REST;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.burgers.app.Data.BurgerRepository;
import com.burgers.app.Data.BurgerSizeRepository;
import com.burgers.app.Data.IngredientRepository;
import com.burgers.app.Domain.Burger;
import com.burgers.app.Domain.BurgerSize;
import com.burgers.app.Domain.Ingredient;
import com.burgers.app.Domain.Order;
import com.burgers.app.Domain.Ingredient.IngredientType;
import com.burgers.app.Exception.BurgersException;
import com.burgers.app.Request.BurgerRequest;
import com.burgers.app.Request.RemoveBurgerRequest;
import com.burgers.app.Request.RemoveIngredientRequest;
import com.burgers.app.Security.MessageResponse;
import com.burgers.app.Service.DesignService;
import com.burgers.app.Service.MapperService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/design")
@CrossOrigin(origins = "http://localhost:8081", allowedHeaders = "*", allowCredentials = "true" ,maxAge = 3600)
public class DesignBurgerRestController {

    @Autowired
    private DesignService designService;

    @Autowired
    private MapperService mapperService;

    @PostMapping("/add")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> addBurger(@Valid @RequestBody BurgerRequest bRequest, HttpServletRequest request) {
        
        Burger burger = mapperService.toEntity(bRequest);
        try {
            return ResponseEntity.ok(designService.addBurger(burger, request.getSession()));
        } catch (BurgersException be) {
            return ResponseEntity.badRequest().body(be.getMessage());
        }
    }

    @PatchMapping("/removeBurger")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> removeBurger(@Valid @RequestBody RemoveBurgerRequest rBurgerRequest, HttpServletRequest request) {

        Burger burger = mapperService.toEntity(rBurgerRequest);
        try {
            return ResponseEntity.ok(designService.removeBurger(burger, request.getSession()));
        } catch (BurgersException be) {
            return ResponseEntity.badRequest().body(be.getMessage());
        }
    }

    @PatchMapping("/removeIngredient")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> removeIngredient(@Valid @RequestBody RemoveIngredientRequest rIngredientRequest, HttpServletRequest request) {

        try {
            Map<String, Object> map = mapperService.toEntity(rIngredientRequest);
            Burger burger = (Burger) map.get("Burger");
            Ingredient ingredient = (Ingredient) map.get("Ingredient");

            return ResponseEntity.ok(designService.removeIngredientFromBurger(burger, ingredient, request.getSession()));
        } catch (BurgersException be) {
            return ResponseEntity.badRequest().body(be.getMessage());
        }
    }

    @Autowired
    private BurgerSizeRepository burgerSizeRepository;

    @GetMapping("/getSizes")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getBurgerSizes() {
        
        return ResponseEntity.ok(burgerSizeRepository.findAll());
    }

    /*
    @Autowired
    IngredientRepository ingredientRepository;

    @Autowired
    private BurgerSizeRepository burgerSizeRepository;

    @PostMapping("/add")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> addBurgerToOrder(@Valid @RequestBody BurgerRequest burger, HttpServletRequest request) {
        List<Ingredient> ingredients = new ArrayList<>();

        if(burger.getIngredients().isEmpty())
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Burger should have at least 1 (one) ingredient!"));

        burger.getIngredients().stream()
        .forEach(ingredient -> {
            Ingredient ing = ingredientRepository.findByName(ingredient);
            if(ing != null)
                ingredients.add(ing);

            else throw new RuntimeException("Error: Ingredient is not found.");
        });

        BurgerSize size = burgerSizeRepository.findByName(burger.getSize());
        if(Objects.isNull(size)){
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Invalid burger size!"));
        }

        Burger burgerToOrder = new Burger(burger.getName(), size, ingredients);

        if(!burgerToOrder.isValid()){
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Invalid burger"));
        }
        
        Order order = (Order) request.getSession().getAttribute("userOrder");

        if (Objects.isNull(order)){
            order = new Order();
            request.getSession().setAttribute("userOrder", order);
        }
        order.addDesign(burgerToOrder);

        return ResponseEntity.ok(burgerToOrder);
    }

    @PatchMapping("/removeIngredient")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> removeIngredient(@Valid @RequestBody RemoveIngredientRequest removeRequest, HttpServletRequest request){

        Order order = (Order) request.getSession().getAttribute("userOrder");
        if(Objects.isNull(order)){
            return ResponseEntity.badRequest().body(new MessageResponse("Error: No current order found!"));
        }

        Optional<Ingredient> opt_ingredient = ingredientRepository.findById(removeRequest.getIngredientId());
        if(!opt_ingredient.isPresent()){
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Ingredient to remove from doesn't exist!"));
        }

        Ingredient ingredient = opt_ingredient.get();

        if(ingredient.getType()==IngredientType.PROTEIN){
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Can not remove the protein"));
        }

        Optional<Burger> opt_burgerToModify = order.getBurgerByBurgerInOrderId(removeRequest.getBurgerInOrderId());
        if (!opt_burgerToModify.isPresent()){
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Burger to remove ingredient from no found in current order!"));
        }

        Burger burgerToModify = opt_burgerToModify.get();
        if(!burgerToModify.ingredientExistsById(removeRequest.getIngredientId())){
            return ResponseEntity.badRequest().body(new MessageResponse(String.format("Error: Ingredient not found in burger in order with id: %d", removeRequest.getBurgerInOrderId())));
        }

        burgerToModify.getIngredients().remove(ingredient);
        burgerToModify.setPrice(burgerToModify.getPrice() - ingredient.getPrice());

        return ResponseEntity.ok(ingredient);
    }

    @PatchMapping("/removeBurger")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> removeBurger(@Valid @RequestBody RemoveBurgerRequest removeRequest, HttpServletRequest request){
        Order order = (Order) request.getSession().getAttribute("userOrder");
        if(Objects.isNull(order)){
            return ResponseEntity.badRequest().body(new MessageResponse("Error: No current order found!"));
        }

        Optional<Burger> opt_burgerToRemove = order.getBurgerByBurgerInOrderId(removeRequest.getBurgerInOrderId());
        if(!opt_burgerToRemove.isPresent()){
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Burger to remove from no found in current order!"));
        }

        Burger burgerToRemove = opt_burgerToRemove.get();

        order.getBurgers().remove(burgerToRemove);

        return ResponseEntity.ok(burgerToRemove);
    }

    @GetMapping("/getSizes")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getBurgerSizes() {
        return ResponseEntity.ok(burgerSizeRepository.findAll());
    }
    */
}

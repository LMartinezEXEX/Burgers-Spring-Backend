package com.burgers.app.Web.REST;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.burgers.app.DTO.Request.BurgerDTO;
import com.burgers.app.DTO.Request.DeleteBurgerDTO;
import com.burgers.app.DTO.Request.DeleteBurgerIngredientDTO;
import com.burgers.app.Data.BurgerSizeRepository;
import com.burgers.app.Domain.Burger;
import com.burgers.app.Domain.Ingredient;
import com.burgers.app.Exception.BurgersException;
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
    public ResponseEntity<?> addBurger(@Valid @RequestBody BurgerDTO bRequest, HttpServletRequest request) {
        
        Burger burger = mapperService.toEntity(bRequest);
        try {
            return ResponseEntity.ok(designService.addBurger(burger, request.getSession()));
        } catch (BurgersException be) {
            return ResponseEntity.badRequest().body(be.getMessage());
        }
    }

    @PatchMapping("/removeBurger")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> removeBurger(@Valid @RequestBody DeleteBurgerDTO rBurgerRequest, HttpServletRequest request) {

        Burger burger = mapperService.toEntity(rBurgerRequest);
        try {
            return ResponseEntity.ok(designService.removeBurger(burger, request.getSession()));
        } catch (BurgersException be) {
            return ResponseEntity.badRequest().body(be.getMessage());
        }
    }

    @PatchMapping("/removeIngredient")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> removeIngredient(@Valid @RequestBody DeleteBurgerIngredientDTO rIngredientRequest, HttpServletRequest request) {

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
}

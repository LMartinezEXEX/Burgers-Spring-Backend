package com.burgers.app.Web.REST;

import java.util.Arrays;
import java.util.List;

import com.burgers.app.Domain.Ingredient;
import com.burgers.app.Domain.Ingredient.IngredientType;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ingredientType")
@CrossOrigin(origins = "http://localhost:8081", allowedHeaders = "*", allowCredentials = "true" ,maxAge = 3600)
public class IngredientTypeRestController {
    
    @GetMapping("/getou") // API CHANGED!
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getTypes() {
        List<Ingredient.IngredientType> types = Arrays.asList(IngredientType.values());

        return ResponseEntity.ok(types);
    }
}

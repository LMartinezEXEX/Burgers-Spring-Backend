package com.burgers.app.Web.REST;

import java.util.List;

import javax.validation.Valid;

import com.burgers.app.Data.IngredientRepository;
import com.burgers.app.Domain.Ingredient;
import com.burgers.app.Domain.Ingredient.IngredientType;
import com.burgers.app.Exception.BurgersException;
import com.burgers.app.Request.DeleteIngredientRequest;
import com.burgers.app.Request.IngredientRequest;
import com.burgers.app.Security.MessageResponse;
import com.burgers.app.Service.IngredientService;
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
@RequestMapping("/api/ingredient")
@CrossOrigin(origins = "http://localhost:8081", allowedHeaders = "*", allowCredentials = "true" ,maxAge = 3600)
public class IngredientRestController {
    
    @Autowired
    private IngredientService ingredientService;

    @Autowired
    private MapperService mapperService;

    @PostMapping("/add")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> add(@Valid @RequestBody IngredientRequest iRequest) {

        try {
            Ingredient ingredient = mapperService.toEntity(iRequest);

            return ResponseEntity.ok(ingredientService.add(ingredient));
        } catch (BurgersException be) {
            return ResponseEntity.badRequest().body(be.getMessage());
        }
    }

    @PatchMapping("/delete")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> delete(@Valid @RequestBody DeleteIngredientRequest dIngredientRequest) {

        try {
            Ingredient ingredient = mapperService.toEntity(dIngredientRequest);

            return ResponseEntity.ok(ingredientService.delete(ingredient));
        } catch (BurgersException be) {
            return ResponseEntity.badRequest().body(be.getMessage());
        }

    }

    @GetMapping("/get")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getAll() {

        return ResponseEntity.ok(ingredientService.getAll());
    }

    @GetMapping("/getTypes")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getTypes() {

        return ResponseEntity.ok(ingredientService.getAllTypes());
    }

    /*
    @Autowired
    IngredientRepository ingredientRepository;

    @PostMapping("/add")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> addIngredient(@Valid @RequestBody IngredientRequest ingredientRequest){
        
        System.out.println(ingredientRequest.getType());
        if (ingredientRepository.existsByName(ingredientRequest.getName())){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Ingredient name already exist!"));
        }

        IngredientType type;

        try {

            type = IngredientType.valueOf(ingredientRequest.getType());

        } catch (IllegalArgumentException e) {
            return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Error: Invalid ingredient type!"));
        }

        Ingredient ingredientToSave = new Ingredient(ingredientRequest.getName().toUpperCase(),
                                                        ingredientRequest.getPrice(),
                                                        ingredientRequest.getDescription(),
                                                        type);
                                
        ingredientRepository.save(ingredientToSave);

        return ResponseEntity.ok(new MessageResponse("Ingreident added successfully!"));
    }

    @PatchMapping("/delete")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteIngredient(@Valid @RequestBody DeleteIngredientRequest deleteRequest){
        ingredientRepository.deleteById(deleteRequest.getIngredientId());
        return ResponseEntity.ok(new MessageResponse("Ingredient deleted Successfull"));
    }


    @RequestMapping("/get")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getIngredients(){
        Iterable<Ingredient> ingredients = ingredientRepository.findAll();

        return ResponseEntity.ok(ingredients);
    } */

}

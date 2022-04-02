package mealsapp.controller;

import lombok.AllArgsConstructor;
import mealsapp.dto.PantryIngredientDto;
import mealsapp.model.PantryIngredient;
import mealsapp.model.Response;
import mealsapp.service.PantryIngredientService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.OK;

@Controller
@RequestMapping("/api/pantry")
@AllArgsConstructor
public class PantryController {

    private final PantryIngredientService pantryIngredientService;

    @PostMapping
    public ResponseEntity<Response> addIngredient(@RequestBody PantryIngredientDto pantryIngredientDto) {
        PantryIngredientDto pantryIngredient = pantryIngredientService.addIngredient(pantryIngredientDto);
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("pantryIngredient", pantryIngredient))
                        .message("Ingredient added to the pantry successfully")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

}

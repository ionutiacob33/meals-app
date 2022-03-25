package mealsapp.controller;

import lombok.AllArgsConstructor;
import mealsapp.dto.RecipeDto;
import mealsapp.model.Recipe;
import mealsapp.model.Response;
import mealsapp.model.User;
import mealsapp.service.RecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/recipe")
@AllArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    @PostMapping
    public ResponseEntity<Response> addRecipe(@RequestBody RecipeDto recipeDto) {
        Recipe recipe = recipeService.addRecipe(recipeDto);
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("recipe", recipe))
                        .message("Recipe created successfully")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }
}

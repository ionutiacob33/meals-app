package mealsapp.controller;

import lombok.AllArgsConstructor;
import mealsapp.dto.RecipeDto;
import mealsapp.model.Recipe;
import mealsapp.model.Response;
import mealsapp.service.RecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @PostMapping("/multiple")
    public ResponseEntity<Response> addRecipes(@RequestBody List<RecipeDto> recipeDtos) {
        List<Recipe> recipes = recipeService.addRecipes(recipeDtos);
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("recipes", recipes))
                        .message("Recipes created successfully")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateRecipe(@RequestBody RecipeDto recipeDto, @PathVariable Long id) {
        Recipe recipe = recipeService.updateRecipe(id, recipeDto);
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("recipe", recipe))
                        .message("Recipe updated successfully")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @GetMapping
    public ResponseEntity<Response> getRecipes() {
        List<RecipeDto> recipes = recipeService.getAllRecipes();
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("recipes", recipes))
                        .message("Recipes retrieved successfully")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getRecipeDetails(@PathVariable Long id) {
        RecipeDto recipe = recipeService.getRecipe(id);
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("recipe", recipe))
                        .message("Recipe retrieved successfully")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteRecipe(@PathVariable Long id) {
        boolean recipeResult = recipeService.deleteRecipe(id);
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("recipeDeleted", recipeResult))
                        .message("Recipe deleted successfully")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }
}

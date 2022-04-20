package mealsapp.controller;

import lombok.AllArgsConstructor;
import mealsapp.model.Response;
import mealsapp.model.api.recipeIntructions.RecipeInstructionsResponse;
import mealsapp.model.api.recipeSummary.RecipeSummaryResponse;
import mealsapp.model.api.searchByIngredient.RecipeByIngredientsItem;
import mealsapp.model.api.searchByIngredient.RecipeByIngredientsRequest;
import mealsapp.model.api.searchByName.RecipeByNameItem;
import mealsapp.model.api.searchByName.RecipeByNameRequest;
import mealsapp.model.api.searchByName.RecipeByNameResponse;
import mealsapp.service.RecipeSearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/recipeSearch")
@AllArgsConstructor
public class RecipeSearchController {
    RecipeSearchService recipeSearchService;

    @GetMapping("/name")
    public ResponseEntity<Response> getRecipesByName(@RequestBody RecipeByNameRequest request) {
        String recipeName = request.getRecipeName();
        String recipeFormat = recipeName.substring(1, recipeName.length() - 1);
        RecipeByNameResponse recipeSearchByNameResponse = recipeSearchService.getRecipesByName(recipeFormat);
        List<RecipeByNameItem> recipes = recipeSearchByNameResponse.getResults();
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("recipes", recipes))
                        .message("Recipe retrieved successfully")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @GetMapping("/ingredient")
    public ResponseEntity<Response> getRecipesByIngredients(@RequestBody RecipeByIngredientsRequest request) {
        List<String> ingredients = request.getIngredients();
        List<RecipeByIngredientsItem> recipes = recipeSearchService.getRecipesByIngredients(ingredients);
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("recipes", recipes))
                        .message("Recipe retrieved successfully")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @GetMapping("/summary/{id}")
    public ResponseEntity<Response> getRecipeSummary(@PathVariable Long id) {
        RecipeSummaryResponse recipeSummary = recipeSearchService.getRecipeSummary(id);
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("summary", recipeSummary))
                        .message("Recipe retrieved successfully")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @GetMapping("/analyzed/{id}")
    public ResponseEntity<Response> getAnalyzedRecipe(@PathVariable Long id) {
        List<RecipeInstructionsResponse> recipeInstructions = recipeSearchService.getAnalyzedRecipe(id);
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("recipes", recipeInstructions))
                        .message("Recipe retrieved successfully")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }


}

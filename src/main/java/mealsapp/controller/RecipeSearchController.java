package mealsapp.controller;

import lombok.AllArgsConstructor;
import mealsapp.model.Response;
import mealsapp.model.api.RecipeSearchByNameItem;
import mealsapp.model.api.RecipeSearchByNameResponse;
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
    public ResponseEntity<Response> getRecipe(@RequestBody String recipeName) {
        String recipeFormat = recipeName.substring(1, recipeName.length() - 1);
        RecipeSearchByNameResponse recipeSearchByNameResponse = recipeSearchService.getRecipe(recipeFormat);
        List<RecipeSearchByNameItem> recipes = recipeSearchByNameResponse.getResults();
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

}

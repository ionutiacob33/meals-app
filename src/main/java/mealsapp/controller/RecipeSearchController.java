package mealsapp.controller;

import lombok.AllArgsConstructor;
import mealsapp.service.RecipeSearchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/recipeSearch")
@AllArgsConstructor
public class RecipeSearchController {
    RecipeSearchService recipeSearchService;

    @GetMapping()
    public void getRecipe() {
        recipeSearchService.getRecipe();
    }

}

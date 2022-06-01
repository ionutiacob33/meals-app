package mealsapp.model.api.recipeDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeDetailsResponse {
    private Long id;
    private String title;
    private String image;
    private String imageType;
    private Integer servings;
    private Integer readyInMinutes;
    private String instructions;
    private Integer aggregateLikes;
    private List<RecipeDetailsIngredient> extendedIngredients;
    public Boolean vegan;
    public Boolean vegetarian;
    public Boolean glutenFree;
    public Boolean dairyFree;
    public Boolean veryHealthy;
    public Boolean veryPopular;
}

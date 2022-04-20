package mealsapp.model.api.searchByIngredient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeSearchByIngredientItem {
    private Integer id;
    private String title;
    private String image;
    private String imageType;
    private Integer likes;
    private Integer missedIngredientsCount;
    private List<RecipeSearchByIngredientsIngredient> missedIngredients;
    private Integer usedIngredientsCount;
    private List<RecipeSearchByIngredientsIngredient> usedIngredients;
    private List<RecipeSearchByIngredientsIngredient> unusedIngredients;
}


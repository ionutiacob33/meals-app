package mealsapp.model.api.searchByIngredient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeByIngredientsItem {
    private Integer id;
    private String title;
    private String image;
    private String imageType;
    private Integer likes;
    private Integer missedIngredientsCount;
    private List<RecipeByIngredientsIngredient> missedIngredients;
    private Integer usedIngredientsCount;
    private List<RecipeByIngredientsIngredient> usedIngredients;
    private List<RecipeByIngredientsIngredient> unusedIngredients;
}


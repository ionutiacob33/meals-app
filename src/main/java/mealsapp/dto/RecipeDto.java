package mealsapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeDto {
    private String title;
    private String description;
    private List<RecipeIngredientDto> recipeIngredients;
}

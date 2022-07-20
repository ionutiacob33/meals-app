package mealsapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeDto {
    private Long id;
    private Long apiId;
    private Long userId;
    private String title;
    private String description;
    private List<IngredientDto> ingredients;
    private List<StepDto> recipeSteps;
    private String imageUrl;
    private Integer calories;
    private Integer protein;
    private Integer fat;
    private Integer carbs;
}

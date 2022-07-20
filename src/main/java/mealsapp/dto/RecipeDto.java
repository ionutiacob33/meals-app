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
    private Long userId;
    private String title;
    private String description;
    private List<IngredientDto> ingredients;
    private List<StepDto> steps;
    private List<CookingTimeDto> cookingTime;
    private String imageUrl;
    private String source;
    private String url;
    private Integer yeald;
}

package mealsapp.model.api.recipeIntructions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeInstructionsResponse {
    private String name;
    private List<Step> steps;
}

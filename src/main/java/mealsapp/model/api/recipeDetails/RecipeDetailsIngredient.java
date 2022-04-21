package mealsapp.model.api.recipeDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeDetailsIngredient {
    private Long id;
    private String aisle;
    private String image;
    private String name;
    private Double amount;
    private String unit;
    private String originalString;
    private List<String> metaInformation;
}

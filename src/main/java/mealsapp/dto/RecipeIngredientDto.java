package mealsapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mealsapp.model.Ingredient;
import mealsapp.model.Quantity;
import mealsapp.model.Unit;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeIngredientDto {
    private String ingredient;
    private String unit;
    private Double quantity;
}

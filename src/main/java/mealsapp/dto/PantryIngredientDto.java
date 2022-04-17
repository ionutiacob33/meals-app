package mealsapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PantryIngredientDto {
    private String ingredient;
    private String unit;
    private Integer quantity;
}

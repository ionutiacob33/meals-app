package mealsapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingListIngredientDto {
    private Long id;
    private String name;
    private String unit;
    private Double amount;
}

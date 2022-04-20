package mealsapp.model.api.searchByName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeByNameRequest {
    private String recipeName;
}

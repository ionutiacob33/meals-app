package mealsapp.model.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeSearchByNameResponse {
    private List<RecipeSearchByNameItem> results;
    private Integer offset;
    private Integer number;
    private Integer totalResults;
}

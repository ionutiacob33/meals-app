package mealsapp.model.api.searchByName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeByNameResponse {
    private List<RecipeByNameItem> results;
    private Integer offset;
    private Integer number;
    private Integer totalResults;
}

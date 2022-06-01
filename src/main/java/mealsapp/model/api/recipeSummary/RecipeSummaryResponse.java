package mealsapp.model.api.recipeSummary;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeSummaryResponse {
    private Long id;
    private String title;
    private String summary;
}

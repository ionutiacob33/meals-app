package mealsapp.model.api.searchByName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeByNameItem {
    private Integer id;
    private String title;
    private String image;
    private String imageType;
}

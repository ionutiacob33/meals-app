package mealsapp.model.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeSearchByNameItem {
    private Integer id;
    private String title;
    private String image;
    private String imageType;
}

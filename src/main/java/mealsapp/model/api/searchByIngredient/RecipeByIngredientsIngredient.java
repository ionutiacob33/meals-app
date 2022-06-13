package mealsapp.model.api.searchByIngredient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeByIngredientsIngredient {
    private Integer id;
    private String name;
    private String aisle;
    private String image;
    private String original;
    private String originalName;
    private Double amount;
    private String unit;
    private String unitLong;
    private String unitShort;
    private List<String> meta;
}
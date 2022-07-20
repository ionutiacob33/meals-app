package mealsapp.model.recipe;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mealsapp.model.recipe.ingredient.Ingredient;

import javax.persistence.*;

import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Recipe {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private Long userId;
    private String title;
    private String description;
    private String imageUrl;

    @OneToMany(mappedBy = "recipe")
    private List<Ingredient> ingredients;
    @OneToMany(mappedBy = "recipe")
    private List<Step> steps;
    @OneToMany(mappedBy = "recipe")
    private List<CookingTime> cookingTimes;

    private String source;
    private String url;
    private Integer yeald;

}

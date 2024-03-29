package mealsapp.model.recipe.ingredient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mealsapp.model.recipe.Recipe;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Ingredient {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "recipe_id", referencedColumnName = "id")
    private Recipe recipe;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "name_id", referencedColumnName = "id")
    private Name name;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "unit_id", referencedColumnName = "id")
    private Unit unit;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "amount_id", referencedColumnName = "id")
    private Amount amount;
}

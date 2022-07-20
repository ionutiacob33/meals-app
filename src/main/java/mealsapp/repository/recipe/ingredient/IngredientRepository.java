package mealsapp.repository.recipe.ingredient;

import mealsapp.model.recipe.ingredient.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    List<Ingredient> findByRecipeId(Long id);
    void deleteAllByRecipeId(Long id);
}

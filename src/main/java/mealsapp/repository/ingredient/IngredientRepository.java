package mealsapp.repository.ingredient;

import mealsapp.model.ingredient.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    List<Ingredient> findByRecipeId(Long id);
    void deleteAllByRecipeId(Long id);
}

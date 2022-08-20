package mealsapp.repository;

import mealsapp.model.User;
import mealsapp.model.recipe.ingredient.Name;
import mealsapp.model.PantryIngredient;
import mealsapp.model.recipe.ingredient.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PantryIngredientRepository extends JpaRepository<PantryIngredient, Long> {
    List<PantryIngredient> findByUserId(Long userId);
    PantryIngredient findByUserAndNameAndUnit(User user, Name name, Unit unit);
}

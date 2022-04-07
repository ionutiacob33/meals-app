package mealsapp.repository;

import mealsapp.model.Ingredient;
import mealsapp.model.PantryIngredient;
import mealsapp.model.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PantryIngredientRepository extends JpaRepository<PantryIngredient, Long> {
    List<PantryIngredient> findByUserId(Long userId);
    PantryIngredient findByIngredientAndUnit(Ingredient ingredient, Unit unit);
}

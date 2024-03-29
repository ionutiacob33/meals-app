package mealsapp.repository;

import mealsapp.model.ShoppingListIngredient;
import mealsapp.model.User;
import mealsapp.model.recipe.ingredient.Name;
import mealsapp.model.recipe.ingredient.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShoppingListRepository extends JpaRepository<ShoppingListIngredient, Long> {
    List<ShoppingListIngredient> findByUserId(Long userId);
    ShoppingListIngredient findByUserAndNameAndUnit(User user, Name name, Unit unit);


}

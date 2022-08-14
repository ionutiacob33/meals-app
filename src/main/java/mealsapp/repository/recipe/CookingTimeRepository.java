package mealsapp.repository.recipe;

import mealsapp.model.recipe.CookingTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CookingTimeRepository extends JpaRepository<CookingTime, Long> {
    List<CookingTime> findByRecipeId(Long recipeId);
    void deleteAllByRecipeId(Long recipeId);
}

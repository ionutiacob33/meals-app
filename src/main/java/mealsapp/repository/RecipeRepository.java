package mealsapp.repository;

import mealsapp.model.Recipe;
import mealsapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findByUserId(Long userId);
}

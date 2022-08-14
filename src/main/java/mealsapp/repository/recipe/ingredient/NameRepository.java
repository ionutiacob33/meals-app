package mealsapp.repository.recipe.ingredient;

import mealsapp.model.recipe.ingredient.Name;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NameRepository extends JpaRepository<Name, Long> {
    Name findByName(String name);
}

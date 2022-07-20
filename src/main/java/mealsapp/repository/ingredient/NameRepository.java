package mealsapp.repository.ingredient;

import mealsapp.model.ingredient.Name;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NameRepository extends JpaRepository<Name, Long> {
    Name findByName(String name);
}

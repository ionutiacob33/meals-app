package mealsapp.repository.recipe.ingredient;

import mealsapp.model.recipe.ingredient.Amount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AmountRepository extends JpaRepository<Amount, Long> {
    Amount findByAmount(Double amount);
}

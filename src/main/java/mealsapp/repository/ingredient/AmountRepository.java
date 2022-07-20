package mealsapp.repository.ingredient;

import mealsapp.model.ingredient.Amount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AmountRepository extends JpaRepository<Amount, Long> {
    Amount findByAmount(Double amount);
}

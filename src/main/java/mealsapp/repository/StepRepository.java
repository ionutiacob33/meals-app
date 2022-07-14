package mealsapp.repository;

import mealsapp.model.Step;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StepRepository extends JpaRepository<Step, Long> {
    Step findByCountAndDescription(int count, String description);
}
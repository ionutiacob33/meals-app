package mealsapp.service;

import lombok.AllArgsConstructor;
import mealsapp.model.Ingredient;
import mealsapp.model.Step;
import mealsapp.repository.StepRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class StepService {

    private final StepRepository stepRepository;

    public Step addStep(int count, String description) {
        Step step = stepRepository.findByCountAndDescription(count, description);
        if (step == null) {
            step = new Step();
            step.setCount(count);
            step.setDescription(description);
            stepRepository.save(step);
        }
        return step;
    }

}

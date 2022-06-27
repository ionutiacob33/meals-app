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

    public Step addStep(Step step) {
        stepRepository.save(step);
        return step;
    }

}

package mealsapp.service.recipe;

import lombok.AllArgsConstructor;
import mealsapp.model.recipe.Step;
import mealsapp.repository.recipe.StepRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class StepService {

    private final StepRepository stepRepository;

    public void add(List<Step> steps) {
        stepRepository.saveAll(steps);
    }

    public List<Step> getByRecipeId(Long recipeId) {
        return stepRepository.findByRecipeId(recipeId);
    }

    public void deleteByRecipeId(Long recipeId) {
        stepRepository.deleteAllByRecipeId(recipeId);
    }

}

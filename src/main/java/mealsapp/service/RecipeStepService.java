package mealsapp.service;

import lombok.AllArgsConstructor;
import mealsapp.model.RecipeStep;
import mealsapp.repository.RecipeStepRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class RecipeStepService {

    private final RecipeStepRepository recipeStepRepository;

    public void add (List<RecipeStep> recipeSteps) {
        recipeStepRepository.saveAll(recipeSteps);
    }

    public List<RecipeStep> getByRecipeId(Long id) { return recipeStepRepository.findByRecipeId(id); }

    public void deleteByRecipeId(Long id) { recipeStepRepository.deleteAllByRecipeId(id); }

}

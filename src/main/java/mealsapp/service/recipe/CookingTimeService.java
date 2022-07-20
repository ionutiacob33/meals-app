package mealsapp.service.recipe;

import lombok.AllArgsConstructor;
import mealsapp.model.recipe.CookingTime;
import mealsapp.repository.recipe.CookingTimeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class CookingTimeService {
    
    private final CookingTimeRepository cookingTimeRepository;
    
    public void add(List<CookingTime> cookingTimes) {
        cookingTimeRepository.saveAll(cookingTimes);
    }

    public List<CookingTime> getByRecipeId(Long recipeId) {
        return cookingTimeRepository.findByRecipeId(recipeId);
    }

    public void deleteByRecipeId(Long recipeId) {
        cookingTimeRepository.deleteAllByRecipeId(recipeId);
    }

}

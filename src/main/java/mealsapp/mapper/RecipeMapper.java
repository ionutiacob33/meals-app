package mealsapp.mapper;

import lombok.AllArgsConstructor;
import mealsapp.dto.CookingTimeDto;
import mealsapp.dto.IngredientDto;
import mealsapp.dto.StepDto;
import mealsapp.model.recipe.CookingTime;
import mealsapp.model.recipe.ingredient.Name;
import mealsapp.model.recipe.ingredient.Amount;
import mealsapp.model.recipe.ingredient.Ingredient;
import mealsapp.model.recipe.ingredient.Unit;
import mealsapp.model.recipe.Recipe;
import mealsapp.model.recipe.Step;
import mealsapp.service.recipe.ingredient.NameService;
import mealsapp.service.recipe.ingredient.AmountService;
import mealsapp.service.recipe.StepService;
import mealsapp.service.recipe.ingredient.UnitService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class RecipeMapper {

    private final NameService nameService;
    private final UnitService unitService;
    private final AmountService amountService;
    private final StepService stepService;

    public List<Ingredient> mapIngredientsDtoToModel(
            List<IngredientDto> ingredientDtos, Recipe recipe) {
        return ingredientDtos.stream()
                .map(
                        ingredientDto ->
                                createIngredientModelFromDto(ingredientDto, recipe)
                )
                .collect(Collectors.toList());
    }

    private Ingredient createIngredientModelFromDto(
            IngredientDto ingredientDto, Recipe recipe) {
        Ingredient ingredient = new Ingredient();
        ingredient.setRecipe(recipe);
        ingredient.setId(ingredientDto.getId());

        Name name = nameService.addName(ingredientDto.getName());
        ingredient.setName(name);

        Unit unit = unitService.addUnit(ingredientDto.getUnit());
        ingredient.setUnit(unit);

        Amount amount = amountService.addAmount(ingredientDto.getAmount());
        ingredient.setAmount(amount);

        return ingredient;
    }

    public List<Step> mapStepsDtoToModel(
            List<StepDto> steps, Recipe recipe) {
        return steps.stream()
                .map(stepDto -> createStepModelFromDto(stepDto, recipe))
                .collect(Collectors.toList());
    }

    private Step createStepModelFromDto(
            StepDto stepDto, Recipe recipe) {
        Step step = new Step();
        step.setId(stepDto.getId());
        step.setRecipe(recipe);
        step.setCount(stepDto.getCount());
        step.setDescription(stepDto.getDescription());

        return step;
    }

    public IngredientDto mapIngredientToDto(
            Ingredient ingredient) {
        IngredientDto ingredientDto = new IngredientDto();

        ingredientDto.setId(ingredient.getId());
        ingredientDto.setName(ingredient.getName().getName());
        ingredientDto.setUnit(ingredient.getUnit().getUnit());
        ingredientDto.setAmount(ingredient.getAmount().getAmount());

        return ingredientDto;
    }

    public StepDto mapStepToDto(
            Step step) {
        StepDto stepDto = new StepDto();

        stepDto.setId(step.getId());
        stepDto.setCount(step.getCount());
        stepDto.setDescription(step.getDescription());

        return stepDto;
    }

    public List<CookingTime> mapCookingTimesDtoToModel(
            List<CookingTimeDto> cookingTimes, Recipe recipe) {
        return cookingTimes.stream()
                .map(
                        cookingTimeDto -> createCookingTimeModelFromDto(cookingTimeDto, recipe)
                )
                .collect(Collectors.toList());
    }

    private CookingTime createCookingTimeModelFromDto(
            CookingTimeDto cookingTimeDto, Recipe recipe) {
        CookingTime cookingTime = new CookingTime();

        cookingTime.setId(cookingTimeDto.getId());
        cookingTime.setRecipe(recipe);
        cookingTime.setTitle(cookingTimeDto.getTitle());
        cookingTime.setMinutes(cookingTimeDto.getMinutes());
        cookingTime.setHours(cookingTimeDto.getHours());

        return cookingTime;
    }

    public CookingTimeDto mapCookingTimeToDto(
            CookingTime cookingTime) {
        CookingTimeDto cookingTimeDto = new CookingTimeDto();

        cookingTimeDto.setId(cookingTime.getId());
        cookingTimeDto.setTitle(cookingTime.getTitle());
        cookingTimeDto.setMinutes(cookingTime.getMinutes());
        cookingTimeDto.setHours(cookingTime.getHours());

        return cookingTimeDto;
    }
}

package mealsapp.mapper;

import lombok.AllArgsConstructor;
import mealsapp.dto.IngredientDto;
import mealsapp.dto.StepDto;
import mealsapp.model.*;
import mealsapp.model.ingredient.Name;
import mealsapp.model.ingredient.Amount;
import mealsapp.model.ingredient.Ingredient;
import mealsapp.model.ingredient.Unit;
import mealsapp.model.step.RecipeStep;
import mealsapp.model.step.Step;
import mealsapp.service.ingredient.NameService;
import mealsapp.service.ingredient.AmountService;
import mealsapp.service.step.StepService;
import mealsapp.service.ingredient.UnitService;
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

    public List<Ingredient> mapIngredientsDtoToModel(List<IngredientDto> ingredientDtos, Recipe recipe) {
        return ingredientDtos.stream()
                .map(ingredientDto -> createIngredientModelFromDto(ingredientDto, recipe))
                .collect(Collectors.toList());
    }

    public Ingredient createIngredientModelFromDto(IngredientDto ingredientDto, Recipe recipe) {
        Ingredient ingredient = new Ingredient();
        ingredient.setRecipe(recipe);
        ingredient.setId(ingredientDto.getId());

        Name name = nameService.addName(ingredientDto.getName());
        ingredient.setName(name);

        Unit unit = unitService.addUnit(ingredientDto.getUnit());
        ingredient.setUnit(unit);

        Amount amount = amountService.addQuantity(ingredientDto.getAmount());
        ingredient.setAmount(amount);

        return ingredient;
    }

    public List<RecipeStep> mapStepsDtoToModel(List<StepDto> recipeSteps, Recipe recipe) {
        return recipeSteps.stream()
                .map(stepDto -> createStepModelFromDto(stepDto, recipe))
                .collect(Collectors.toList());
    }

    public RecipeStep createStepModelFromDto(StepDto stepDto, Recipe recipe) {
        RecipeStep recipeStep = new RecipeStep();
        recipeStep.setRecipe(recipe);
        recipeStep.setId(stepDto.getId());

        Step step = new Step();
        step.setCount(stepDto.getCount());
        step.setDescription(stepDto.getDescription());
        recipeStep.setStep(stepService.addStep(step.getCount(), step.getDescription()));

        return recipeStep;
    }

    public IngredientDto mapIngredientToDto(Ingredient ingredient) {
        IngredientDto ingredientDto = new IngredientDto();

        ingredientDto.setId(ingredient.getId());
        ingredientDto.setName(ingredient.getName().getName());
        ingredientDto.setUnit(ingredient.getUnit().getUnit());
        ingredientDto.setAmount(ingredient.getAmount().getAmount());

        return ingredientDto;
    }

    public StepDto mapStepToDto(RecipeStep recipeStep) {
        StepDto stepDto = new StepDto();
        Step step = recipeStep.getStep();

        stepDto.setId(step.getId());
        stepDto.setCount(step.getCount());
        stepDto.setDescription(step.getDescription());

        return stepDto;
    }

}

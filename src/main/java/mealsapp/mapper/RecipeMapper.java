package mealsapp.mapper;

import lombok.AllArgsConstructor;
import mealsapp.dto.RecipeIngredientDto;
import mealsapp.dto.StepDto;
import mealsapp.model.*;
import mealsapp.service.IngredientService;
import mealsapp.service.QuantityService;
import mealsapp.service.StepService;
import mealsapp.service.UnitService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class RecipeMapper {

    private final IngredientService ingredientService;
    private final UnitService unitService;
    private final QuantityService quantityService;
    private final StepService stepService;

    public List<RecipeIngredient> mapIngredientsDtoToModel(List<RecipeIngredientDto> recipeIngredientDtos, Recipe recipe) {
        return recipeIngredientDtos.stream()
                .map(recipeIngredientDto -> createIngredientModelFromDto(recipeIngredientDto, recipe))
                .collect(Collectors.toList());
    }

    public RecipeIngredient createIngredientModelFromDto(RecipeIngredientDto recipeIngredientDto, Recipe recipe) {
        RecipeIngredient recipeIngredient = new RecipeIngredient();
        recipeIngredient.setRecipe(recipe);
        recipeIngredient.setId(recipeIngredientDto.getId());

        Ingredient ingredient = ingredientService.addIngredient(recipeIngredientDto.getIngredient());
        recipeIngredient.setIngredient(ingredient);

        Unit unit = unitService.addUnit(recipeIngredientDto.getUnit());
        recipeIngredient.setUnit(unit);

        Quantity quantity = quantityService.addQuantity(recipeIngredientDto.getQuantity());
        recipeIngredient.setQuantity(quantity);

        return recipeIngredient;
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
        recipeStep.setStep(stepService.addStep(step));

        return recipeStep;
    }

    public RecipeIngredientDto mapIngredientToDto(RecipeIngredient recipeIngredient) {
        RecipeIngredientDto recipeIngredientDto = new RecipeIngredientDto();

        recipeIngredientDto.setId(recipeIngredient.getId());
        recipeIngredientDto.setIngredient(recipeIngredient.getIngredient().getName());
        recipeIngredientDto.setUnit(recipeIngredient.getUnit().getName());
        recipeIngredientDto.setQuantity(recipeIngredient.getQuantity().getAmount());

        return recipeIngredientDto;
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

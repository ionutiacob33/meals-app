package mealsapp.mapper;

import lombok.AllArgsConstructor;
import mealsapp.dto.ShoppingListIngredientDto;
import mealsapp.model.ShoppingListIngredient;
import mealsapp.model.recipe.ingredient.Amount;
import mealsapp.model.recipe.ingredient.Name;
import mealsapp.model.recipe.ingredient.Unit;
import mealsapp.service.AuthService;
import mealsapp.service.recipe.ingredient.AmountService;
import mealsapp.service.recipe.ingredient.NameService;
import mealsapp.service.recipe.ingredient.UnitService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class ShoppingListMapper {

    private final AuthService authService;
    private final NameService nameService;
    private final UnitService unitService;
    private final AmountService amountService;

    public ShoppingListIngredientDto mapToDto(
            ShoppingListIngredient shoppingListIngredient) {
        ShoppingListIngredientDto shoppingListIngredientDto = new ShoppingListIngredientDto();
        shoppingListIngredientDto.setId(shoppingListIngredient.getId());
        shoppingListIngredientDto.setName(shoppingListIngredient.getName().getName());
        shoppingListIngredientDto.setUnit(shoppingListIngredient.getUnit().getUnit());
        shoppingListIngredientDto.setAmount(shoppingListIngredient.getAmount().getAmount());

        return shoppingListIngredientDto;
    }

    public ShoppingListIngredient mapToModel(
            ShoppingListIngredientDto shoppingListIngredientDto) {
        ShoppingListIngredient shoppingListIngredient = new ShoppingListIngredient();

        shoppingListIngredient.setId(shoppingListIngredientDto.getId());
        shoppingListIngredient.setUser(authService.getAuthenticatedUser());

        Name name = nameService.addName(shoppingListIngredientDto.getName());
        shoppingListIngredient.setName(name);

        Unit unit = unitService.addUnit(shoppingListIngredientDto.getUnit());
        shoppingListIngredient.setUnit(unit);

        Amount amount = amountService.addAmount(shoppingListIngredientDto.getAmount());
        shoppingListIngredient.setAmount(amount);

        return shoppingListIngredient;
    }

}

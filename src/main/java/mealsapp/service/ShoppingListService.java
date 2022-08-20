package mealsapp.service;

import lombok.AllArgsConstructor;
import mealsapp.dto.ShoppingListIngredientDto;
import mealsapp.error.GenericException;
import mealsapp.mapper.ShoppingListMapper;
import mealsapp.model.ShoppingListIngredient;
import mealsapp.model.recipe.ingredient.Amount;
import mealsapp.repository.ShoppingListRepository;
import mealsapp.service.recipe.ingredient.AmountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class ShoppingListService {

    private final ShoppingListRepository shoppingListRepository;
    private final ShoppingListMapper shoppingListMapper;
    private final AmountService amountService;
    private final AuthService authService;

    public ShoppingListIngredientDto addIngredient(ShoppingListIngredientDto shoppingListIngredientDto) {
        shoppingListIngredientDto = toLower(shoppingListIngredientDto);

        ShoppingListIngredient shoppingListIngredientMap = shoppingListMapper.mapToModel(shoppingListIngredientDto);
        ShoppingListIngredient shoppingListIngredient = shoppingListRepository
                .findByUserAndNameAndUnit(
                        authService.getAuthenticatedUser(),
                        shoppingListIngredientMap.getName(),
                        shoppingListIngredientMap.getUnit()
                );

        if (shoppingListIngredient != null) {
            Amount amount = amountService.incrementAmount(shoppingListIngredient.getAmount(), shoppingListIngredientMap.getAmount());
            shoppingListIngredient.setAmount(amount);
        } else {
            shoppingListIngredient = new ShoppingListIngredient();
            shoppingListIngredient.setUser(authService.getAuthenticatedUser());
            shoppingListIngredient.setName(shoppingListIngredientMap.getName());
            shoppingListIngredient.setAmount(shoppingListIngredientMap.getAmount());
            shoppingListIngredient.setUnit(shoppingListIngredientMap.getUnit());
        }
        shoppingListRepository.save(shoppingListIngredient);

        return shoppingListMapper.mapToDto(shoppingListIngredient);
    }

    public List<ShoppingListIngredientDto> addIngredients(List<ShoppingListIngredientDto> shoppingListIngredientDtos) {
        List<ShoppingListIngredientDto> shoppingListIngredients = new ArrayList<>();

        for (ShoppingListIngredientDto shoppingListIngredientDto : shoppingListIngredientDtos) {
            shoppingListIngredients.add(addIngredient(shoppingListIngredientDto));
        }

        return shoppingListIngredients;
    }

    public List<ShoppingListIngredientDto> getIngredientsOfCurrentUser() {
        Long userId = authService.getAuthenticatedUser().getId();
        List<ShoppingListIngredient> shoppingListIngredients = shoppingListRepository.findByUserId(userId);

        return shoppingListIngredients.stream()
                .map(shoppingListMapper::mapToDto)
                .toList();
    }

    public ShoppingListIngredientDto editIngredient(Long id, ShoppingListIngredientDto shoppingListIngredientDto) {
        shoppingListIngredientDto = toLower(shoppingListIngredientDto);

        ShoppingListIngredient shoppingListIngredient = shoppingListRepository.getById(id);
        ShoppingListIngredient shoppingListIngredientMap = shoppingListMapper.mapToModel(shoppingListIngredientDto);

        shoppingListIngredient.setName(shoppingListIngredientMap.getName());
        shoppingListIngredient.setUnit(shoppingListIngredientMap.getUnit());
        shoppingListIngredient.setAmount(shoppingListIngredientMap.getAmount());

        shoppingListRepository.save(shoppingListIngredient);

        return shoppingListIngredientDto;
    }

    public boolean deleteIngredient(Long id) {
        ShoppingListIngredient shoppingListIngredient = shoppingListRepository.getById(id);
        Long userId = shoppingListIngredient.getUser().getId();
        Long authUserId = authService.getAuthenticatedUser().getId();

        if (userId != authUserId) {
            throw new GenericException("User can only delete its own shopping list ingredients");
        }

        shoppingListRepository.deleteById(id);

        return true;
    }

    private ShoppingListIngredientDto toLower(ShoppingListIngredientDto shoppingListIngredientDto) {
        String lowerName = shoppingListIngredientDto.getName().toLowerCase();
        String lowerUnit = shoppingListIngredientDto.getUnit().toLowerCase();

        shoppingListIngredientDto.setName(lowerName);
        shoppingListIngredientDto.setUnit(lowerUnit);

        return shoppingListIngredientDto;
    }

}

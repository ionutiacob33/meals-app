package mealsapp.service;

import lombok.AllArgsConstructor;
import mealsapp.dto.PantryIngredientDto;
import mealsapp.mapper.PantryMapper;
import mealsapp.model.Ingredient;
import mealsapp.model.PantryIngredient;
import mealsapp.model.Quantity;
import mealsapp.model.Unit;
import mealsapp.repository.PantryIngredientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class PantryIngredientService {

    private final PantryIngredientRepository pantryIngredientRepository;
    private final PantryMapper pantryMapper;

    public PantryIngredientDto addIngredient(PantryIngredientDto pantryIngredientDto) {
        PantryIngredient pantryIngredient = pantryMapper.mapToModel(pantryIngredientDto);
        pantryIngredientRepository.save(pantryIngredient);

        return pantryIngredientDto;
    }

    public List<PantryIngredientDto> getIngredients(Long userId) {
        List<PantryIngredient> pantryIngredients = pantryIngredientRepository.findByUserId(userId);

        return pantryIngredients.stream()
                .map(pantryMapper::mapToDto)
                .toList();
    }

}

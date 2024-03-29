package mealsapp.service.recipe.ingredient;

import lombok.AllArgsConstructor;
import mealsapp.model.recipe.ingredient.Unit;
import mealsapp.repository.recipe.ingredient.UnitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class UnitService {

    private final UnitRepository unitRepository;

    public Unit addUnit(String unit) {
        Unit unitData = unitRepository.findByUnit(unit);
        if (unitData == null) {
            unitData = new Unit();
            unitData.setUnit(unit);
            unitRepository.save(unitData);
        }
        return unitData;
    }

}

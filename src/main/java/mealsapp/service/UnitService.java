package mealsapp.service;

import lombok.AllArgsConstructor;
import mealsapp.model.Unit;
import mealsapp.repository.UnitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class UnitService {

    private final UnitRepository unitRepository;

    public Unit addUnit(String name) {
        Unit unit = unitRepository.findByName(name);
        if (unit == null) {
            unit = new Unit();
            unit.setName(name);
            unitRepository.save(unit);
        }
        return unit;
    }

}

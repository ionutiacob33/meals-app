package mealsapp.service.ingredient;

import lombok.AllArgsConstructor;
import mealsapp.model.ingredient.Name;
import mealsapp.repository.ingredient.NameRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class NameService {

    private final NameRepository nameRepository;

    public Name addName(String name) {
        Name nameData = nameRepository.findByName(name);
        if (nameData == null) {
            nameData = new Name();
            nameData.setName(name);
            nameRepository.save(nameData);
        }
        return nameData;
    }

}

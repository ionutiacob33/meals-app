package mealsapp.model.step;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Step {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private Integer count;
    private String description;
}

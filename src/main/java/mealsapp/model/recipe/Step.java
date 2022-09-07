package mealsapp.model.recipe;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Step {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Lob
    private String description;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "recipe_id", referencedColumnName = "id")
    private Recipe recipe;

    private Integer count;
}

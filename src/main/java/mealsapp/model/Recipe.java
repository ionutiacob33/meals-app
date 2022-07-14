package mealsapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Recipe {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private Long userId;
    private Long apiId;
    private String title;
    private String description;
    private String imageUrl;
    private Integer calories;
    private Integer protein;
    private Integer fat;
    private Integer carbs;

}

package mealsapp.service;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import static mealsapp.utils.Constants.*;
import static org.springframework.http.HttpMethod.GET;

@Service
@AllArgsConstructor
@Transactional
public class RecipeSearchService {

    public static final String RECIPES_COMPLEX_SEARCH = "/recipes/complexSearch";
    private RestTemplate restTemplate;

    public void getRecipe() {
        HttpEntity<String> entity = new HttpEntity<String>("", httpHeaders());
        ResponseEntity<String> response = restTemplate.exchange(SPOONACULAR_URL + RECIPES_COMPLEX_SEARCH + "?number=2&minCalories=500&minProtein=20&minFat=10&minCarbs=50", GET, entity, String.class);
        System.out.println("Result - status ("+ response.getStatusCode() + ") has body: " + response.hasBody());
        if (response.hasBody()) {
            System.out.println(response.getBody());
        }
    }

    private HttpHeaders httpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(X_RAPID_API_HOST, SPOONACULAR_URL);
        headers.add(X_RAPID_API_KEY, API_KEY);
        return headers;
    }

}


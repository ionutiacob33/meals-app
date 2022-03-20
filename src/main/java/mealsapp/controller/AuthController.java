package mealsapp.controller;

import lombok.AllArgsConstructor;
import mealsapp.dto.RegisterRequest;
import mealsapp.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest) {
        if (authService.signup(registerRequest)) {
            return new ResponseEntity("User Registration Successful", HttpStatus.OK);
        } else {
            return new ResponseEntity("User Registration Failed", HttpStatus.CONFLICT);
        }
    }

}

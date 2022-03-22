package mealsapp.controller;

import lombok.AllArgsConstructor;
import mealsapp.dto.LoginRequest;
import mealsapp.dto.RegisterRequest;
import mealsapp.model.Response;
import mealsapp.model.User;
import mealsapp.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<Response> signup(@RequestBody RegisterRequest registerRequest) {
        User user = authService.signup(registerRequest);
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("user", user))
                        .message("User created successfully")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @GetMapping("/accountVerification/{token}")
    public ResponseEntity<Response> verifyToken(@PathVariable String token) {
        authService.verifyAccount(token);
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("token", token))
                        .message("User activated successfully")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

//    @PostMapping("/login")
//    public ResponseEntity<Response> login(@RequestBody LoginRequest loginRequest) {
//        String loggedInUser = loginRequest.getUsername();
//        return ResponseEntity.ok(
//                Response.builder()
//                        .timeStamp(now())
//                        .data(Map.of("loggedInUser", loggedInUser))
//                        .message("User logged in successfully")
//                        .status(OK)
//                        .statusCode(OK.value())
//                        .build()
//        );
//    }

}

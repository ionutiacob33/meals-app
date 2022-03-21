package mealsapp.service;

import lombok.AllArgsConstructor;
import mealsapp.dto.RegisterRequest;
import mealsapp.error.EmailAlreadyExistsException;
import mealsapp.error.UsernameAlreadyExistsException;
import mealsapp.mail.MailService;
import mealsapp.model.NotificationEmail;
import mealsapp.model.User;
import mealsapp.model.VerificationToken;
import mealsapp.repository.UserRepository;
import mealsapp.repository.VerificationTokenRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User signup(RegisterRequest registerRequest) throws UsernameAlreadyExistsException, EmailAlreadyExistsException {
        if (!isUsernameUnique(registerRequest.getUsername())) {
            throw new UsernameAlreadyExistsException();
        }

        if (!isEmailUnique(registerRequest.getEmail())) {
            throw new EmailAlreadyExistsException();
        }

        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreated(Instant.now());
        user.setEnabled(false);

        userRepository.save(user);

        String token = generateVerificationToken(user);
        mailService.sendMail(new NotificationEmail(
                "Please Activate your Account",
                user.getEmail(),
                "Thank you for signing up to Spring Reddit, " +
                        "please click on the below url to activate your account : " +
                        "http://localhost:8080/api/auth/accountVerification/" + token
        ));

        return user;
    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);

        verificationTokenRepository.save(verificationToken);
        return token;
    }

    private boolean isUsernameUnique(String username) {
        return userRepository.findByUsername(username).isEmpty();
    }

    private boolean isEmailUnique(String email) {
        return userRepository.findByEmail(email).isEmpty();
    }

}

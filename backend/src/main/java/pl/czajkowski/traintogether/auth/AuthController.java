package pl.czajkowski.traintogether.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.czajkowski.traintogether.auth.models.LoginRequest;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @PostMapping
    public ResponseEntity<?> login(LoginRequest request) {
        return ResponseEntity.ok(null);
    }
}

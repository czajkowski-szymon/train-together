package pl.czajkowski.traintogether.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.czajkowski.traintogether.auth.models.LoginRequest;
import pl.czajkowski.traintogether.auth.models.LoginResponse;
import pl.czajkowski.traintogether.user.models.UserDTO;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping
    public ResponseEntity<UserDTO> authenticate(Authentication user) {
        return ResponseEntity.ok(authService.authenticate(user.getName()));
    }
}

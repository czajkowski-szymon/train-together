package pl.czajkowski.traintogether.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.czajkowski.traintogether.user.models.RegistrationRequest;
import pl.czajkowski.traintogether.user.models.User;
import pl.czajkowski.traintogether.user.models.UserDTO;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegistrationRequest request) {
        User user = userService.register(request);
        return ResponseEntity.created(URI.create("/users/" + user.getUserId())).body(user);
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(null);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(null);
    }

    @PutMapping
    public ResponseEntity<UserDTO> updateUser(User user) {
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer userId) {
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{userId}/profile-picture")
    public ResponseEntity<?> uploadProfilePicture(@PathVariable Integer userId) {
        return ResponseEntity.created(URI.create("/api/v1/users/%d/profile-picture".formatted(userId))).body(null);
    }

    @GetMapping("/{userId}/profile-picture")
    public ResponseEntity<?> downloadProfileImage(@PathVariable Integer userId) {
        return ResponseEntity.ok(null);
    }
}

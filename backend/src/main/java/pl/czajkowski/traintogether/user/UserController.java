package pl.czajkowski.traintogether.user;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.czajkowski.traintogether.user.models.RegistrationRequest;
import pl.czajkowski.traintogether.user.models.UpdateUserRequest;
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
    public ResponseEntity<UserDTO> register(@RequestBody RegistrationRequest request) {
        UserDTO user = userService.register(request);
        return ResponseEntity.created(URI.create("/users/" + user.userId())).body(user);
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(userService.getUser(userId));
    }

    @PutMapping
    public ResponseEntity<UserDTO> updateUser(@RequestBody UpdateUserRequest request, Authentication user) {
        return ResponseEntity.ok(userService.updateUser(request, user.getName()));
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteUser(Authentication user) {
        userService.deleteUser(user.getName());
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

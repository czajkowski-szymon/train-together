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
    public ResponseEntity<List<UserDTO>> getAllUsers(Authentication user) {
        return ResponseEntity.ok(userService.getAllUsers(user.getName()));
    }

    @GetMapping("/city")
    public ResponseEntity<List<UserDTO>> getAllUsersByCity(@RequestParam(name = "cityname") String city, Authentication user) {
        return ResponseEntity.ok(userService.getAllUsersByCity(city, user.getName()));
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }

    @GetMapping("/isfriend/{userId}")
    public boolean isUserFriend(@PathVariable Integer userId, Authentication user) {
        return userService.isUserFriend(userId, user.getName());
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

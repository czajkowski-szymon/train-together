package pl.czajkowski.traintogether.user;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.czajkowski.traintogether.auth.models.LoginRequest;
import pl.czajkowski.traintogether.rabbitmq.RabbitMQConfig;
import pl.czajkowski.traintogether.sport.Sport;
import pl.czajkowski.traintogether.user.models.RegistrationRequest;
import pl.czajkowski.traintogether.user.models.UpdateUserRequest;
import pl.czajkowski.traintogether.user.models.UserDTO;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final RabbitTemplate template;

    public UserController(UserService userService, RabbitTemplate template) {
        this.userService = userService;
        this.template = template;
    }

    @PostMapping("/publish")
    public String publish(@RequestBody LoginRequest request) {
        template.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY, request);
        return "Wyslano";
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

    @GetMapping("/{userId}/sports")
    public List<Sport> getAllUsersSports(@PathVariable Integer userId) {
        return userService.getAllUsersSports(userId);
    }

    @GetMapping("/isavailable/{username}/{email}")
    public boolean checkIfUsernameAndEmailAreAvailable(@PathVariable("username") String username,
                                                       @PathVariable("email") String email) {
        return userService.checkIfUsernameAndEmailAreAvailable(username, email);
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

    @PostMapping(
            value = "/{userId}/profile-picture",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public void uploadProfilePicture(@PathVariable("userId") Integer userId,
                                                  @RequestParam("file") MultipartFile file) {
        userService.uploadProfilePicture(userId, file);
    }

    @GetMapping(
            value = "/{userId}/profile-picture",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public byte[] downloadProfileImage(@PathVariable("userId") Integer userId) {
        return userService.downloadProfileImage(userId);
    }
}

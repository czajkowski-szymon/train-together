package pl.czajkowski.traintogether.friendship;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.czajkowski.traintogether.user.models.UserDTO;

import java.util.List;

@RestController
@RequestMapping("api/v1/friendships")
public class FriendshipController {

    private final FriendshipService friendshipService;

    public FriendshipController(FriendshipService friendshipService) {
        this.friendshipService = friendshipService;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getFriends(Authentication user) {
        return ResponseEntity.ok(friendshipService.getFriends(user.getName()));
    }
}

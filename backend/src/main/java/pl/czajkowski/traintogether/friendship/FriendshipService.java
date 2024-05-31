package pl.czajkowski.traintogether.friendship;

import org.springframework.stereotype.Service;
import pl.czajkowski.traintogether.exception.UserNotFoundException;
import pl.czajkowski.traintogether.user.UserMapper;
import pl.czajkowski.traintogether.user.UserRepository;
import pl.czajkowski.traintogether.user.models.User;
import pl.czajkowski.traintogether.user.models.UserDTO;

import java.util.List;

@Service
public class FriendshipService {

    private final FriendshipRepository friendshipRepository;

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public FriendshipService(FriendshipRepository friendshipRepository,
                             UserRepository userRepository,
                             UserMapper userMapper) {
        this.friendshipRepository = friendshipRepository;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public List<UserDTO> getFriends(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UserNotFoundException("User not found")
        );
        List<User> allFriendsOfUser = userRepository.findAllFriendsOfUser(user.getUserId());
        return allFriendsOfUser.stream().map(userMapper::toUserDTO).toList();
    }
}

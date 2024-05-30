package pl.czajkowski.traintogether.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.czajkowski.traintogether.exception.ResourceNotFoundException;
import pl.czajkowski.traintogether.exception.UserNotFoundException;
import pl.czajkowski.traintogether.exception.UsernameOrEmailAlreadyExistsException;
import pl.czajkowski.traintogether.city.CityRepository;
import pl.czajkowski.traintogether.friendship.FriendshipRepository;
import pl.czajkowski.traintogether.user.models.*;

import java.util.List;

import static pl.czajkowski.traintogether.user.models.Role.USER;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final CityRepository cityRepository;

    private final FriendshipRepository friendshipRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder encoder;

    public UserService(UserRepository userRepository,
                       CityRepository cityRepository,
                       FriendshipRepository friendshipRepository,
                       UserMapper userMapper,
                       PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.cityRepository = cityRepository;
        this.friendshipRepository = friendshipRepository;
        this.userMapper = userMapper;
        this.encoder = encoder;
    }

    public UserDTO register(RegistrationRequest request) {
        validateUsernameAndEmail(request.username(), request.email());
        User user = createUserFromRequest(request);

        return userMapper.toUserDTO(userRepository.save(user));
    }

    public List<UserDTO> getAllUsers(String username) {
        return userRepository.findAllExceptGivenUser(username)
                .stream()
                .map(userMapper::toUserDTO)
                .toList();
    }

    public List<UserDTO> getAllUsersByCity(String city, String username) {
        return userRepository.findAllByCity(cityRepository.findByName(city).orElseThrow(
                        () -> new ResourceNotFoundException("City not found")
                ))
                .stream()
                .map(userMapper::toUserDTO)
                .toList();
    }

    public UserDTO getUserByUsername(String username) {
        return userMapper.toUserDTO(userRepository.findByUsername(username).orElseThrow(
                () -> new UserNotFoundException("User with username: %s not found".formatted(username))
        ));
    }

    public UserDTO updateUser(UpdateUserRequest request, String email) {
        validateUsernameAndEmail(request.username(), request.email());
        return userMapper.toUserDTO(retrieveAndUpdateUser(request, email));
    }


    public void deleteUser(String username) {
        userRepository.deleteByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new UserNotFoundException("User with username: '%s' not found".formatted(username))
        );
    }

    public boolean isUserFriend(Integer userId, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UserNotFoundException("User with username: '%s' not found".formatted(username))
        );
        return friendshipRepository.exists(userId, user.getUserId());
    }

    private User createUserFromRequest(RegistrationRequest request) {
        User user = new User();
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setFirstName(request.firstName());
        user.setPassword(encoder.encode(request.password()));
        user.setDateOfBirth(request.dateOfBirth());
        user.setGender(request.gender());
        user.setBio(request.bio());
        user.setCity(cityRepository.findByName(request.city()).get());
        user.setRole(USER);

        return user;
    }

    private User retrieveAndUpdateUser(UpdateUserRequest request, String username) {
        User user = (User) loadUserByUsername(username);
        user.setUsername(request.username());
        user.setEmail(request.username());
        user.setFirstName(request.firstName());
        user.setDateOfBirth(request.dateOfBirth());
        user.setGender(request.gender());
        user.setBio(request.username());
        user.setCity(cityRepository.findByName(request.city()).get());

        return userRepository.save(user);
    }

    private void validateUsernameAndEmail(String username, String email) {
        if (userRepository.existsByUsernameOrEmail(username, email)) {
            throw new UsernameOrEmailAlreadyExistsException(
                    "User with username: '%s' or email: '%s' already exists".formatted(username, email)
            );
        }
    }

}

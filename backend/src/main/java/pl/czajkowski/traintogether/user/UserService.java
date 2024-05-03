package pl.czajkowski.traintogether.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.czajkowski.traintogether.user.models.*;

import java.util.List;

import static pl.czajkowski.traintogether.user.models.Role.ADMIN;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final CityRepository cityRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder encoder;

    public UserService(UserRepository userRepository,
                       CityRepository cityRepository,
                       UserMapper userMapper,
                       PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.cityRepository = cityRepository;
        this.userMapper = userMapper;
        this.encoder = encoder;
    }

    public UserDTO register(RegistrationRequest request) {
        validateUsernameAndEmail(request.username(), request.email());
        User user = createUserFromRequest(request);

        return userMapper.toUserDTO(userRepository.save(user));
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toUserDTO)
                .toList();
    }

    public UserDTO getUser(Integer userId) {
        // TODO: Custom exception
        return userMapper.toUserDTO(userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("User not found")
        ));
    }

    public UserDTO updateUser(UpdateUserRequest request, String email) {
        return userMapper.toUserDTO(retrieveAndUpdateUser(request, email));
    }


    public void deleteUser(Integer userId, String username) {
        User user = (User) loadUserByUsername(username);
        validateUser(user, username);

        userRepository.deleteById(userId);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User with username: " + username + " not found")
        );
    }

    private User createUserFromRequest(RegistrationRequest request) {
        User user = new User();
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setPassword(encoder.encode(request.password()));
        user.setDateOfBirth(request.dateOfBirth());
        user.setGender(request.gender());
        user.setBio(request.bio());
        user.setCity(cityRepository.findByName(request.city()).get());
        user.setRole(ADMIN);

        return user;
    }

    private User retrieveAndUpdateUser(UpdateUserRequest request, String username) {
        User user = (User) loadUserByUsername(username);
        validateUser(user, username);

        user.setUsername(request.username());
        user.setEmail(request.username());
        user.setDateOfBirth(request.dateOfBirth());
        user.setGender(request.gender());
        user.setBio(request.username());
        user.setCity(cityRepository.findByName(request.city()).get());

        return userRepository.save(user);
    }

    private void validateUsernameAndEmail(String username, String email) {
        if (userRepository.existsByUsernameOrEmail(username, email)) {
            throw new RuntimeException("User with username: " + username + " or email: " + email + " already exists");
        }
    }

    private void validateUser(User user, String username) {
        if (!user.getUsername().equals(username)) {
            // TODO: Custom exception
            throw new RuntimeException("User can`t update information about other user");
        }
    }
}

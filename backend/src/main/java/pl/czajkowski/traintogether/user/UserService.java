package pl.czajkowski.traintogether.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.czajkowski.traintogether.exception.UserNotFoundException;
import pl.czajkowski.traintogether.exception.UsernameOrEmailAlreadyExistsException;
import pl.czajkowski.traintogether.city.CityRepository;
import pl.czajkowski.traintogether.user.models.*;

import java.util.List;

import static pl.czajkowski.traintogether.user.models.Role.USER;

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
        return userMapper.toUserDTO(userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User with id: %d not found".formatted(userId))
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

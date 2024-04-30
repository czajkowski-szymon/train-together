package pl.czajkowski.traintogether.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.czajkowski.traintogether.user.models.RegistrationRequest;
import pl.czajkowski.traintogether.user.models.Role;
import pl.czajkowski.traintogether.user.models.User;
import pl.czajkowski.traintogether.user.models.UserDTO;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final CityRepository cityRepository;

    private final RoleRepository roleRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder encoder;

    public UserService(UserRepository userRepository,
                       CityRepository cityRepository,
                       RoleRepository roleRepository,
                       UserMapper userMapper,
                       PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.cityRepository = cityRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
        this.encoder = encoder;
    }

    public UserDTO register(RegistrationRequest request) {
        validateUsernameAndEmail(request.username(), request.email());
        User user = createUserFromRequest(request);
        return userMapper.toUserDTO(userRepository.save(user));
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
        user.setRole(roleRepository.findById(2).get());

        return user;
    }

    private void validateUsernameAndEmail(String username, String email) {
        if (userRepository.existsByUsernameOrEmail(username, email)) {
            throw new RuntimeException("User with username: " + username + " or email: " + email + " already exists");
        }
    }
}

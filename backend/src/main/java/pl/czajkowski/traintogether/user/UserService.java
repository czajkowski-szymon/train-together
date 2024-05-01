package pl.czajkowski.traintogether.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.czajkowski.traintogether.user.models.RegistrationRequest;
import pl.czajkowski.traintogether.user.models.Role;
import pl.czajkowski.traintogether.user.models.User;
import pl.czajkowski.traintogether.user.models.UserDTO;

import static pl.czajkowski.traintogether.user.models.Role.ADMIN;

@Service
public class UserService {

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

    public User register(RegistrationRequest request) {
        validateUsernameAndEmail(request.username(), request.email());
        User user = createUserFromRequest(request);
        return userRepository.save(user);
//        return userMapper.toUserDTO(userRepository.save(user));
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

    private void validateUsernameAndEmail(String username, String email) {
        if (userRepository.existsByUsernameOrEmail(username, email)) {
            throw new RuntimeException("User with username: " + username + " or email: " + email + " already exists");
        }
    }
}

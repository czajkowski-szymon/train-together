package pl.czajkowski.traintogether.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.czajkowski.traintogether.exception.ResourceNotFoundException;
import pl.czajkowski.traintogether.exception.UserNotFoundException;
import pl.czajkowski.traintogether.exception.UsernameOrEmailAlreadyExistsException;
import pl.czajkowski.traintogether.city.CityRepository;
import pl.czajkowski.traintogether.friendship.FriendshipRepository;
import pl.czajkowski.traintogether.sport.Sport;
import pl.czajkowski.traintogether.sport.SportRepository;
import pl.czajkowski.traintogether.user.models.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

import static pl.czajkowski.traintogether.user.models.Role.USER;

@Service
public class UserService implements UserDetailsService {

    private static final String UPLOAD_DIRECTORY = "../frontend/src/assets/uploads/";

    private final UserRepository userRepository;

    private final CityRepository cityRepository;

    private final FriendshipRepository friendshipRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder encoder;

    private final SportRepository sportRepository;

    public UserService(UserRepository userRepository,
                       CityRepository cityRepository,
                       FriendshipRepository friendshipRepository,
                       UserMapper userMapper,
                       PasswordEncoder encoder,
                       SportRepository sportRepository) {
        this.userRepository = userRepository;
        this.cityRepository = cityRepository;
        this.friendshipRepository = friendshipRepository;
        this.userMapper = userMapper;
        this.encoder = encoder;
        this.sportRepository = sportRepository;
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
        return userRepository.findAllByCityExceptGivenUser(cityRepository.findByName(city).orElseThrow(
                        () -> new ResourceNotFoundException("City not found")
                ), username)
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

    public List<Sport> getAllUsersSports(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User not found")
        );
        return user.getSports();
    }

    public void uploadProfilePicture(Integer userId, MultipartFile file) {
        File uploadDir = new File(UPLOAD_DIRECTORY);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        UUID uuid = UUID.randomUUID();

        String[] splitedString = Objects.requireNonNull(file.getOriginalFilename()).split("\\.");
        int index = splitedString.length - 1;
        String fileName = uuid.toString() + "." + splitedString[index];
        User user = userRepository.findById(userId).orElseThrow();
        user.setProfilePictureId(fileName);
        userRepository.save(user);

        Path filePath = Paths.get(UPLOAD_DIRECTORY, fileName);

        try {
            Files.write(filePath, file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
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
        user.setSports(sportRepository.findAllById(Arrays.asList(request.sportIds())));
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

    public byte[] downloadProfileImage(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow();
        try {
            try (Stream<Path> files = Files.list(Paths.get(UPLOAD_DIRECTORY))) {
                Path filePath = files
                        .filter(path -> path.getFileName().toString().equals(user.getProfilePictureId()))
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("File not found"));

                return Files.readAllBytes(filePath);
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not retrieve the file. Error: " + e.getMessage());
        }
    }

    public boolean checkIfUsernameAndEmailAreAvailable(String username, String email) {
        return !userRepository.existsByUsernameOrEmail(username, email);
    }
}

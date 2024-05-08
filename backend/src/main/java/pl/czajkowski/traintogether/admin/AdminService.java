package pl.czajkowski.traintogether.admin;

import org.springframework.stereotype.Service;
import pl.czajkowski.traintogether.exception.UserNotFoundException;
import pl.czajkowski.traintogether.user.UserRepository;
import pl.czajkowski.traintogether.user.models.User;

@Service
public class AdminService {

    private final UserRepository userRepository;

    public AdminService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void blockUser(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User with id: %d not found".formatted(userId))
        );
        user.setEnabled(false);
        userRepository.save(user);
    }

    public void deleteUser(Integer userId) {
        userRepository.deleteById(userId);
    }
}

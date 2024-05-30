package pl.czajkowski.traintogether.user;

import org.springframework.stereotype.Service;
import pl.czajkowski.traintogether.user.models.User;
import pl.czajkowski.traintogether.user.models.UserDTO;

@Service
public class UserMapper {

    public UserDTO toUserDTO(User user) {
        return new UserDTO(
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                user.getFirstName(),
                user.getProfilePictureId(),
                user.getDateOfBirth(),
                user.getGender(),
                user.getBio(),
                user.getCity(),
                user.getRole(),
                user.getSports()
        );
    }
}

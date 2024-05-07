package pl.czajkowski.traintogether.friendship;

import org.springframework.stereotype.Service;
import pl.czajkowski.traintogether.friendship.models.FriendshipInvitation;
import pl.czajkowski.traintogether.friendship.models.FriendshipInvitationDTO;
import pl.czajkowski.traintogether.user.UserMapper;

@Service
public class FriendshipMapper {

    private final UserMapper userMapper;

    public FriendshipMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public FriendshipInvitationDTO toFriendshipInvitationDTO(FriendshipInvitation invitation) {
        return new FriendshipInvitationDTO(
                invitation.getFriendshipInvitationId(),
                invitation.getSendAt(),
                invitation.isAccepted(),
                userMapper.toUserDTO(invitation.getSender()),
                userMapper.toUserDTO(invitation.getReceiver())
        );
    }
}

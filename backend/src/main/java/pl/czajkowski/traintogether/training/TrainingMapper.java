package pl.czajkowski.traintogether.training;

import org.springframework.stereotype.Service;
import pl.czajkowski.traintogether.training.models.Training;
import pl.czajkowski.traintogether.training.models.TrainingDTO;
import pl.czajkowski.traintogether.training.models.TrainingInvitation;
import pl.czajkowski.traintogether.training.models.TrainingInvitationDTO;
import pl.czajkowski.traintogether.user.UserMapper;

@Service
public class TrainingMapper {

    private final UserMapper userMapper;

    public TrainingMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public TrainingDTO toTrainingDTO(Training training) {
        return new TrainingDTO(
                training.getTrainingId(),
                training.getDate(),
                training.getSport(),
                userMapper.toUserDTO(training.getParticipantOne()),
                userMapper.toUserDTO(training.getParticipantTwo())
        );
    }

    public TrainingInvitationDTO toTrainingInvitationDTO(TrainingInvitation invitation) {
        return new TrainingInvitationDTO(
                invitation.getTrainingInvitationId(),
                invitation.getDate(),
                invitation.getSport(),
                invitation.isAccepted(),
                userMapper.toUserDTO(invitation.getSender()),
                userMapper.toUserDTO(invitation.getReceiver())
        );
    }
}

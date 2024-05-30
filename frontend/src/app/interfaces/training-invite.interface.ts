import { Sport } from "./sport.interface";
import { User } from "./user.interface";

export interface TrainingInvite {
    trainingInvitationId: number,
    date: Date,
    sport: Sport,
    message: string,
    sender: User,
    receiver: User
}
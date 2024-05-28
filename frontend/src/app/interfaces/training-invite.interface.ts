import { Sport } from "./sport.interface";
import { User } from "./user.interface";

export interface TrainingInvite {
    inviteId: number,
    date: Date,
    sport: Sport,
    sender: User,
    receiver: User
}
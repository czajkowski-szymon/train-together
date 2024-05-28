import { Sport } from "./sport.interface";
import { User } from "./user.interface";

export interface Training {
    trainingId: number,
    date: Date,
    sport: Sport,
    participantOne: User,
    participantTwo: User
}
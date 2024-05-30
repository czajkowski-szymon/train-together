import { Sport } from "./sport.interface";

export interface TrainingInviteRequest {
    date: Date,
    sport: string,
    message: string,
    senderId: number,
    receiverId: number
}
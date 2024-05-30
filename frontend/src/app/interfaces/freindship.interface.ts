import { User } from "./user.interface";

export interface Friendship {
    friendshipId: number,
    created: Date,
    memberOne: User,
    memberTwo: User
}
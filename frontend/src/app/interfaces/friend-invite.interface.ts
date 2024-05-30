import { User } from "./user.interface";

export interface FriendInvite {
    friendshipInvitationId: number,
    sendAt: Date,
    isAccepted: boolean,
    sender: User,
    receiver: User
}
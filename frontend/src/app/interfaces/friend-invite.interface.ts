import { User } from "./user.interface";

export interface FriendInvite {
    inviteId: number,
    sendAt: Date,
    isAccepted: boolean,
    sender: User,
    receiver: User
}
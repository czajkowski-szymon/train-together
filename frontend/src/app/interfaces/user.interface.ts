import { City } from "./city.interface";
import { Sport } from "./sport.interface";

export interface User {
    userId: number;
    username: string;
    email: string;
    firstName: string;
    profilePictureId: string
    dateOfBirth: Date;
    gender: string;
    bio: string;
    city: City
    role: string;
    sports: Array<Sport>;
}
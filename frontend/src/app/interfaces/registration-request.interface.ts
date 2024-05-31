export interface RegistrationRequest {
    username: string;
    email: string;
    password: string;
    firstName: string;
    dateOfBirth?: Date;
    gender: string;
    bio: string;
    city: string;
    sportIds: number[];
}
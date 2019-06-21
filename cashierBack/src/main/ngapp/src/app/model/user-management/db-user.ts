export interface DbUser {
  id: string;
  username: string;
  email: string;
  name: string;
  surname: string;
  dateOfBirth: Date;
  password: string;
  initialized: boolean;
  role: string;
}

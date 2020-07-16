export interface User {

  serialCode: string;
  firstName: string;
  lastName: string;
  dtIns?: Date;
  dtLastAccess?: Date;
  role?: number;
  authData?: string;
}

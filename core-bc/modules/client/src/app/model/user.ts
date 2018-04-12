import {UsersRoles} from "./UsersRoles";
import {Ansvarig} from "./Ansvarig";

export class User {
  userName: string;

  // Obsolete start
  firstName: string;
  lastName: string;
  mail: string;
  role: string;
  inactivated: boolean;
  // Obsolete end

  ansvariga: Array<Ansvarig>;
  usersRoles: Array<UsersRoles>;

  name: string;
  passWord: string;
  typ: string;

  sjukskoterska: boolean;
  admin: boolean;
  pharmaceut: boolean;

}

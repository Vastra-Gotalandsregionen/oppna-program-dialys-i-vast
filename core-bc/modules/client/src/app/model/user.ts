import {Ansvarig} from "./Ansvarig";
import {Mottagning} from "./Mottagning";

export class User {
  userName: string;

  // Obsolete start
  firstName: string;
  lastName: string;
  mail: string;
  role: string;
  inactivated: boolean;
  // Obsolete end

  // ansvariga: Array<Ansvarig> = [];

  /*anstallnings: Array<Anstallning> = [];*/
  mottagnings: Array<Mottagning> = [];

  name: string;
  passWord: string;
  typ: string;

  sjukskoterska: boolean;
  admin: boolean;
  pharmaceut: boolean;

}

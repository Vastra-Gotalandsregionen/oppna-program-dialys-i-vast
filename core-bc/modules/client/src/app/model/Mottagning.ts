import {User} from "./user";
import {Ansvarig} from "./Ansvarig";

export class Mottagning {

  id: number;
  namn: string;
  apotekID: number;

  ansvarigs: Array<Ansvarig> = [];

}

import {Patient} from "./Patient";
import {BestInfo} from "./BestInfo";
import {Grupp} from "./Grupp";

export class Flik {
  grupps: Array<Grupp>;
  id: number;
  ordning: number;
  titel: string;
  typ: string;
  aktiv: boolean;
}

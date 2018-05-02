import {Patient} from "./Patient";
import {BestInfo} from "./BestInfo";
import {Grupp} from "./Grupp";
import {FlikRot} from "./FlikRot";

export class Flik {
  grupps: Array<Grupp> = [];
  id: number;
  ordning: number;
  titel: string;
  typ: string;
  aktiv: boolean;
  flikRot: FlikRot = new FlikRot();
}

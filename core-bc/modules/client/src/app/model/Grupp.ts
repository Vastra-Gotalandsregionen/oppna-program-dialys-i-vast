import {Patient} from "./Patient";
import {BestInfo} from "./BestInfo";
import {Flik} from "./Flik";
import {Artikel} from "./Artikel";

export class Grupp {
  artikels: Array<Artikel>;
  flik: Flik;
  flikID: number;
  id: number;
  ordning: number;
  titel: string;
}

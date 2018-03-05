/*import {Prodn1} from "./prodn1";
import {Prodn3} from "./prodn3";*/
import {Pd} from "./Pd";
export class Patient {
  adress: string;
  efternamn: string;
  epost: string;
  fornamn: string;
  id: number;
  isDeleted: boolean;
  mobil: string;
  pas: number;
  pnr: string;
  portkod: string;
  postNr: string;
  postOrt: string;
  samtycke: boolean;
  telefon: string;
  utdelDag: string;
  utdelText: string;
  utdelVecka: string;
  pds:Array<Pd>
}

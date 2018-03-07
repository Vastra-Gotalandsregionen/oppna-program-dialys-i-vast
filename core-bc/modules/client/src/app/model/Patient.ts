import {Pd} from "./Pd";
import {Ansvarig} from "./Ansvarig";

export class Patient {
  adress: string;
  ansvarig: Ansvarig;
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
  lasText: string;
  ovrigt: string;
  pds: Array<Pd>
}

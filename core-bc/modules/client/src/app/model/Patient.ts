import {Pd} from "./Pd";
import {Mottagning} from "./Mottagning";
import * as moment from "moment";
import _date = moment.unitOfTime._date;

export class Patient {
  adress: string;
  status: string = 'Aktiv';
  efternamn: string;
  epost: string;
  fornamn: string;
  id: number;
  mobil: string;
  pnr: string;
  portkod: string;
  postNr: string;
  postOrt: string;
  samtycke: boolean;
  telefon: string;
  utdelDag: string;
  utdelText: string;
  utdelVecka: string;
  ovrigt: string;

  // About the delivery
  leveransPaminnelse: boolean;
  avropsOmbud: string;
  leveransMottagningsOmbud: string;

  typ: string = 'PD';

  pds: Array<Pd>;

  tempAdressFrom: Date;
  tempAdressTom: Date;
  tempPostNr: string;
  tempPostOrt: string;
  tempAdress: string;

  mottagnings: Array<Mottagning> = [];

  private static template: Patient = new Patient();

  public static init(that: Patient) {
    that.sortPds = this.template.sortPds;
  }

  public sortPds() {
    if (this.pds != null) {
      this.pds.sort((a: Pd, b: Pd) =>
        ((a.datum > b.datum || (a.datum == b.datum && a.id > b.id)) ? -1 : 1));
      this.pds.forEach((pd: Pd) => {
        if (!pd.sortBestInfos)
          Pd.init(pd);
        pd.sortBestInfos();
      });
    }
  }

}



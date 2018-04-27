import {Patient} from "./Patient";
import {BestInfo} from "./BestInfo";
import {PDArtikel} from "./PDArtikel";

export class Pd {

  datum: Date;
  ersatter: number;
  giltighet: Date;
  id: number;
  las: number;
  patient: Patient;
  patientID: number;
  sskID: number;
  bestInfos: Array<BestInfo> = [];
  pdArtikels: Array<PDArtikel> = [];
  typ: string;

  public sortBestInfos() {
    if (this.bestInfos)
      this.bestInfos.sort((a: BestInfo, b: BestInfo) =>
        (((a.datum + ' ' + a.id) > (b.datum + ' ' + b.id)) ? -1 : 1));
    /*this.bestInfos.sort((a: BestInfo, b: BestInfo) =>
      ((a.datum > b.datum || (a.datum == b.datum && a.id > b.id)) ? -1 : 1));*/
  }

  private static template: Pd = new Pd();

  public static init(pd: Pd) {
    if (!pd.sortBestInfos) {
      pd.sortBestInfos = this.template.sortBestInfos;
    }
  }
}

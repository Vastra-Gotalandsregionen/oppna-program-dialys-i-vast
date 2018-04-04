import {Patient} from "./Patient";
import {BestInfo} from "./BestInfo";
import {Artikel} from "./Artikel";
import {BestPDRad} from "./BestPDRad";

export class PDArtikel {
  artikel: Artikel;
  artikelID: number;
  bestPDRad: Array<BestPDRad>;
  id: number;
  pdid: number;
}

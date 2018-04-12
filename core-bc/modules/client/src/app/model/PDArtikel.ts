import {Patient} from "./Patient";
import {BestInfo} from "./BestInfo";
import {Artikel} from "./Artikel";
import {BestPDRad} from "./BestPDRad";

export class PDArtikel {
  artikel: Artikel;
  artikelID: number;
  bestPDRads: Array<BestPDRad>;
  id: number;
  pdid: number;
  maxantal: number;
}

import {Pd} from "./Pd";
import {BestPDRad} from "./BestPDRad";

export class BestInfo {
  datum: Date;
  fritext: string;
  id: number;
  levDatum: Date;
  pd: Pd;
  pdid: number;
  utskrivare: string;
  bestPDRads: Array<BestPDRad> = [];
}

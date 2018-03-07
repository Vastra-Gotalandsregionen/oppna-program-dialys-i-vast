import {Patient} from "./Patient";
import {BestInfo} from "./BestInfo";

export class Pd {
  datum: Date;
  ersatter: number;
  giltighet: Date;
  id: number;
  las: number;
  patient: Patient;
  patientID: number;
  sskID: number;
  bestInfos: Array<BestInfo>;
}

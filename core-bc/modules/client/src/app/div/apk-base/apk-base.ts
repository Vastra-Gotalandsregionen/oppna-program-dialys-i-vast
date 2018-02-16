import {Util} from "../../core/util/util";
import {Patient} from '../../model/patient';

export class ApkBase {

  constructor() {}

  getStatus(data: Patient) {

    /*const tillDatum = data.tillDatum;

    if (!tillDatum) {
      // No value means "until further notice".
      return 'valid';
    }

    if (Util.isOlderThanXYears(tillDatum, 1)) {
      return 'fullyClosed';
    } else if (Util.isOlderThanXYears(tillDatum, 0)) {
      return 'closed'
    } else {
      return 'valid';
    }*/

    return 'valid';
  }

}

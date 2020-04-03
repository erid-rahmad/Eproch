export interface IReferenceList {
  id?: number;
  name?: string;
  description?: string;
  value?: string;
  referenceId?: number;
}

export class ReferenceList implements IReferenceList {
  constructor(public id?: number, public name?: string, public description?: string, public value?: string, public referenceId?: number) {}
}

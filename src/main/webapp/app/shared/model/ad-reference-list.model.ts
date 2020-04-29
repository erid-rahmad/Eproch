export interface IADReferenceList {
  id?: number;
  name?: string;
  value?: string;
  description?: string;
  active?: boolean;
  adReferenceId?: number;
}

export class ADReferenceList implements IADReferenceList {
  constructor(
    public id?: number,
    public name?: string,
    public value?: string,
    public description?: string,
    public active?: boolean,
    public adReferenceId?: number
  ) {
    this.active = this.active || false;
  }
}

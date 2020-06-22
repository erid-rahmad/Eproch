export interface ICBank {
  id?: number;
  name?: string;
  value?: string;
  shortName?: string;
  description?: string;
  swiftCode?: string;
  active?: boolean;
}

export class CBank implements ICBank {
  constructor(
    public id?: number,
    public name?: string,
    public value?: string,
    public shortName?: string,
    public description?: string,
    public swiftCode?: string,
    public active?: boolean
  ) {
    this.active = this.active || false;
  }
}

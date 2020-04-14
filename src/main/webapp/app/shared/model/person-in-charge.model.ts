export interface IPersonInCharge {
  id?: number;
  position?: string;
  phone?: string;
  userLogin?: string;
  userId?: number;
  vendorId?: number;
  name?: string;
}

export class PersonInCharge implements IPersonInCharge {
  constructor(
    public id?: number,
    public position?: string,
    public phone?: string,
    public userLogin?: string,
    public userId?: number,
    public vendorId?: number,
    public name?: string
  ) {}
}

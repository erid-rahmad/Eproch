export interface ICCurrency {
  id?: number;
  code?: string;
  symbol?: string;
  name?: string;
  active?: boolean;
}

export class CCurrency implements ICCurrency {
  constructor(public id?: number, public code?: string, public symbol?: string, public name?: string, public active?: boolean) {
    this.active = this.active || false;
  }
}

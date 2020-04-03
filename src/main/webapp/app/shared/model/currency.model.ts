export interface ICurrency {
  id?: number;
  code?: string;
  symbol?: string;
  name?: string;
}

export class Currency implements ICurrency {
  constructor(public id?: number, public code?: string, public symbol?: string, public name?: string) {}
}

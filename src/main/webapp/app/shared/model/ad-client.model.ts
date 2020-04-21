export interface IADClient {
  id?: number;
  name?: string;
  code?: string;
  description?: string;
  active?: boolean;
}

export class ADClient implements IADClient {
  constructor(public id?: number, public name?: string, public code?: string, public description?: string, public active?: boolean) {
    this.active = this.active || false;
  }
}

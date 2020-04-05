export interface IPermission {
  id?: number;
  name?: string;
  module?: string;
  canWrite?: boolean;
}

export class Permission implements IPermission {
  constructor(public id?: number, public name?: string, public module?: string, public canWrite?: boolean) {
    this.canWrite = this.canWrite || false;
  }
}

export interface IADFieldGroup {
  id?: number;
  name?: string;
  collapsible?: boolean;
  collapseByDefault?: boolean;
  active?: boolean;
}

export class ADFieldGroup implements IADFieldGroup {
  constructor(
    public id?: number,
    public name?: string,
    public collapsible?: boolean,
    public collapseByDefault?: boolean,
    public active?: boolean
  ) {
    this.collapsible = this.collapsible || false;
    this.collapseByDefault = this.collapseByDefault || false;
    this.active = this.active || false;
  }
}

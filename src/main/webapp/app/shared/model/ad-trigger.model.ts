export const enum AdTriggerType {
  PROCESS = 'PROCESS',
  REPORT = 'REPORT',
  WORKFLOW = 'WORKFLOW'
}

export interface IAdTrigger {
  id?: number;
  active?: boolean;
  name?: string;
  value?: string;
  description?: string;
  type?: AdTriggerType;
  adOrganizationId?: number;
}

export class AdTrigger implements IAdTrigger {
  constructor(
    public id?: number,
    public active?: boolean,
    public name?: string,
    public value?: string,
    public description?: string,
    public type?: AdTriggerType,
    public adOrganizationId?: number
  ) {
    this.active = this.active || true;
  }
}

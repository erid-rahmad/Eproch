export interface IAdValidationRule {
  id?: number;
  uid?: string;
  name?: string;
  description?: string;
  query?: string;
  adValidationRuleId?: number;
}

export class AdValidationRule implements IAdValidationRule {
  constructor(
    public id?: number,
    public uid?: string,
    public name?: string,
    public description?: string,
    public query?: string,
    public adValidationRuleId?: number
  ) {}
}

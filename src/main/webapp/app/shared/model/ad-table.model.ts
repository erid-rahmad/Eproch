import { IADColumn } from '@/shared/model/ad-column.model';

export interface IADTable {
  id?: number;
  name?: string;
  view?: boolean;
  active?: boolean;
  aDColumns?: IADColumn[];
  adOrganizationId?: number;
}

export class ADTable implements IADTable {
  constructor(
    public id?: number,
    public name?: string,
    public view?: boolean,
    public active?: boolean,
    public aDColumns?: IADColumn[],
    public adOrganizationId?: number
  ) {
    this.view = this.view || false;
    this.active = this.active || false;
  }
}

import { IADOrganization } from '@/shared/model/ad-organization.model';

export interface IADClient {
  id?: number;
  name?: string;
  code?: string;
  description?: string;
  active?: boolean;
  aDOrganizations?: IADOrganization[];
}

export class ADClient implements IADClient {
  constructor(
    public id?: number,
    public name?: string,
    public code?: string,
    public description?: string,
    public active?: boolean,
    public aDOrganizations?: IADOrganization[]
  ) {
    this.active = this.active || false;
  }
}

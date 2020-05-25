import { IADReferenceList } from '@/shared/model/ad-reference-list.model';

export const enum ADReferenceType {
  DATATYPE = 'DATATYPE',
  LIST = 'LIST',
  TABLE = 'TABLE'
}

export interface IADReference {
  id?: number;
  name?: string;
  value?: string;
  description?: string;
  referenceType?: ADReferenceType;
  active?: boolean;
  aDReferenceLists?: IADReferenceList[];
}

export class ADReference implements IADReference {
  constructor(
    public id?: number,
    public name?: string,
    public value?: string,
    public description?: string,
    public referenceType?: ADReferenceType,
    public active?: boolean,
    public aDReferenceLists?: IADReferenceList[]
  ) {
    this.active = this.active || false;
  }
}

import { IReferenceList } from '@/shared/model/reference-list.model';

export const enum ReferenceType {
  LIST = 'LIST',
  TABLE = 'TABLE'
}

export interface IReference {
  id?: number;
  name?: string;
  description?: string;
  referenceType?: ReferenceType;
  referenceLists?: IReferenceList[];
}

export class Reference implements IReference {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public referenceType?: ReferenceType,
    public referenceLists?: IReferenceList[]
  ) {}
}

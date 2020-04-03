export interface ISupportingDocument {
  id?: number;
  documentNo?: string;
  expirationDate?: Date;
  fileContentType?: string;
  file?: any;
  typeId?: number;
  vendorId?: number;
}

export class SupportingDocument implements ISupportingDocument {
  constructor(
    public id?: number,
    public documentNo?: string,
    public expirationDate?: Date,
    public fileContentType?: string,
    public file?: any,
    public typeId?: number,
    public vendorId?: number
  ) {}
}

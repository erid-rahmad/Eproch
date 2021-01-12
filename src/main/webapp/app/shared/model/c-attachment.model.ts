export const enum CAttachmentType {
  LOCAL = 'LOCAL',
  REMOTE = 'REMOTE'
}

export interface ICAttachment {
  id?: number;
  type?: CAttachmentType;
  fileName?: string;
  imageSmall?: string;
  imageMedium?: string;
  imageLarge?: string;
  mimeType?: string;
  documentType?: string;
  uploadDir?: string;
  uid?: string;
  active?: boolean;
  adOrganizationId?: number;
}

export class CAttachment implements ICAttachment {
  constructor(
    public id?: number,
    public type?: CAttachmentType,
    public fileName?: string,
    public imageSmall?: string,
    public imageMedium?: string,
    public imageLarge?: string,
    public mimeType?: string,
    public documentType?: string,
    public uploadDir?: string,
    public uid?: string,
    public active?: boolean,
    public adOrganizationId?: number
  ) {
    this.active = this.active || false;
  }
}

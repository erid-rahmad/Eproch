import { ICAttachment } from './c-attachment.model';

export const enum CGalleryItemType {
  DOCUMENT = 'DOCUMENT',
  IMAGE = 'IMAGE',
  VIDEO = 'VIDEO'
}

export interface ICGalleryItem {
  id?: number;
  type?: CGalleryItemType;
  sequence?: number;
  preview?: boolean;
  uid?: string;
  active?: boolean;
  cAttachment?: ICAttachment;
  cAttachmentId?: number;
  adOrganizationId?: number;
  cGalleryId?: number;
}

export class CGalleryItem implements ICGalleryItem {
  constructor(
    public id?: number,
    public type?: CGalleryItemType,
    public sequence?: number,
    public preview?: boolean,
    public uid?: string,
    public active?: boolean,
    public cAttachment?: ICAttachment,
    public cAttachmentId?: number,
    public adOrganizationId?: number,
    public cGalleryId?: number
  ) {
    this.preview = this.preview || false;
    this.active = this.active || false;
  }
}

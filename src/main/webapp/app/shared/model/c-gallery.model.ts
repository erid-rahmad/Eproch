import { ICGalleryItem } from '@/shared/model/c-gallery-item.model';

export interface ICGallery {
  id?: number;
  name?: string;
  description?: string;
  uid?: string;
  active?: boolean;
  cGalleryItems?: ICGalleryItem[];
  adOrganizationId?: number;
}

export class CGallery implements ICGallery {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public uid?: string,
    public active?: boolean,
    public cGalleryItems?: ICGalleryItem[],
    public adOrganizationId?: number
  ) {
    this.active = this.active || false;
  }
}

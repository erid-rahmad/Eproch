import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../page-objects/alert-page';

export default class CGalleryItemUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('opusWebApp.cGalleryItem.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  typeSelect = element(by.css('select#c-gallery-item-type'));

  sequenceInput: ElementFinder = element(by.css('input#c-gallery-item-sequence'));

  previewInput: ElementFinder = element(by.css('input#c-gallery-item-preview'));

  uidInput: ElementFinder = element(by.css('input#c-gallery-item-uid'));

  activeInput: ElementFinder = element(by.css('input#c-gallery-item-active'));
  cAttachmentSelect = element(by.css('select#c-gallery-item-cAttachment'));

  adOrganizationSelect = element(by.css('select#c-gallery-item-adOrganization'));

  cGallerySelect = element(by.css('select#c-gallery-item-cGallery'));
}

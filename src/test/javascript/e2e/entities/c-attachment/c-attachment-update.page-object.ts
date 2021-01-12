import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../page-objects/alert-page';

export default class CAttachmentUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('opusWebApp.cAttachment.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  typeSelect = element(by.css('select#c-attachment-type'));

  fileNameInput: ElementFinder = element(by.css('input#c-attachment-fileName'));

  imageSmallInput: ElementFinder = element(by.css('input#c-attachment-imageSmall'));

  imageMediumInput: ElementFinder = element(by.css('input#c-attachment-imageMedium'));

  imageLargeInput: ElementFinder = element(by.css('input#c-attachment-imageLarge'));

  mimeTypeInput: ElementFinder = element(by.css('input#c-attachment-mimeType'));

  documentTypeInput: ElementFinder = element(by.css('input#c-attachment-documentType'));

  uploadDirInput: ElementFinder = element(by.css('input#c-attachment-uploadDir'));

  uidInput: ElementFinder = element(by.css('input#c-attachment-uid'));

  activeInput: ElementFinder = element(by.css('input#c-attachment-active'));
  adOrganizationSelect = element(by.css('select#c-attachment-adOrganization'));
}

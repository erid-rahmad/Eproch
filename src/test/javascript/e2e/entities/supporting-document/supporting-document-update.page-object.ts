import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../page-objects/alert-page';

export default class SupportingDocumentUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('opusWebApp.supportingDocument.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  documentNoInput: ElementFinder = element(by.css('input#supporting-document-documentNo'));

  expirationDateInput: ElementFinder = element(by.css('input#supporting-document-expirationDate'));

  fileInput: ElementFinder = element(by.css('input#file_file'));

  typeSelect = element(by.css('select#supporting-document-type'));

  vendorSelect = element(by.css('select#supporting-document-vendor'));
}

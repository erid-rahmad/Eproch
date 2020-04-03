import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../page-objects/alert-page';

export default class DocumentTypeUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('opusWebApp.documentType.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  nameInput: ElementFinder = element(by.css('input#document-type-name'));

  descriptionInput: ElementFinder = element(by.css('input#document-type-description'));

  hasExpirationDateInput: ElementFinder = element(by.css('input#document-type-hasExpirationDate'));

  forCompanyInput: ElementFinder = element(by.css('input#document-type-forCompany'));

  forProfessionalInput: ElementFinder = element(by.css('input#document-type-forProfessional'));

  activeInput: ElementFinder = element(by.css('input#document-type-active'));
}

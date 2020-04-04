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

  mandatoryBusinessCategoriesInput: ElementFinder = element(by.css('input#document-type-mandatoryBusinessCategories'));

  additionalBusinessCategoriesInput: ElementFinder = element(by.css('input#document-type-additionalBusinessCategories'));

  mandatoryForCompanyInput: ElementFinder = element(by.css('input#document-type-mandatoryForCompany'));

  mandatoryForProfessionalInput: ElementFinder = element(by.css('input#document-type-mandatoryForProfessional'));

  additionalForCompanyInput: ElementFinder = element(by.css('input#document-type-additionalForCompany'));

  additionalForProfessionalInput: ElementFinder = element(by.css('input#document-type-additionalForProfessional'));

  activeInput: ElementFinder = element(by.css('input#document-type-active'));
}

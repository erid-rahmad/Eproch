import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../page-objects/alert-page';

export default class DocumentTypeBusinessCategoryUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('opusWebApp.documentTypeBusinessCategory.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  mandatoryInput: ElementFinder = element(by.css('input#document-type-business-category-mandatory'));

  additionalInput: ElementFinder = element(by.css('input#document-type-business-category-additional'));
  documentTypeSelect = element(by.css('select#document-type-business-category-documentType'));

  businessCategorySelect = element(by.css('select#document-type-business-category-businessCategory'));
}

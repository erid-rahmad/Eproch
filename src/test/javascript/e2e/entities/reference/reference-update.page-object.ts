import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../page-objects/alert-page';

export default class ReferenceUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('opusWebApp.reference.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  nameInput: ElementFinder = element(by.css('input#reference-name'));

  descriptionInput: ElementFinder = element(by.css('input#reference-description'));

  referenceTypeSelect = element(by.css('select#reference-referenceType'));
}

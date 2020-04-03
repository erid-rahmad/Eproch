import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../page-objects/alert-page';

export default class ReferenceListUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('opusWebApp.referenceList.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  nameInput: ElementFinder = element(by.css('input#reference-list-name'));

  descriptionInput: ElementFinder = element(by.css('input#reference-list-description'));

  valueInput: ElementFinder = element(by.css('input#reference-list-value'));

  referenceSelect = element(by.css('select#reference-list-reference'));
}

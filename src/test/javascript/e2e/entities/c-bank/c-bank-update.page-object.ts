import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../page-objects/alert-page';

export default class CBankUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('opusWebApp.cBank.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  nameInput: ElementFinder = element(by.css('input#c-bank-name'));

  valueInput: ElementFinder = element(by.css('input#c-bank-value'));

  shortNameInput: ElementFinder = element(by.css('input#c-bank-shortName'));

  descriptionInput: ElementFinder = element(by.css('input#c-bank-description'));

  swiftCodeInput: ElementFinder = element(by.css('input#c-bank-swiftCode'));

  activeInput: ElementFinder = element(by.css('input#c-bank-active'));
}

import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../page-objects/alert-page';

export default class CurrencyUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('opusWebApp.currency.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  codeInput: ElementFinder = element(by.css('input#currency-code'));

  symbolInput: ElementFinder = element(by.css('input#currency-symbol'));

  nameInput: ElementFinder = element(by.css('input#currency-name'));
}

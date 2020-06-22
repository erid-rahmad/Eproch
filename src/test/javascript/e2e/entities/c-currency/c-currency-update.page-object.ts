import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../page-objects/alert-page';

export default class CCurrencyUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('opusWebApp.cCurrency.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  codeInput: ElementFinder = element(by.css('input#c-currency-code'));

  symbolInput: ElementFinder = element(by.css('input#c-currency-symbol'));

  nameInput: ElementFinder = element(by.css('input#c-currency-name'));

  activeInput: ElementFinder = element(by.css('input#c-currency-active'));
}

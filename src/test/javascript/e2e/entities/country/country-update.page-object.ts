import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../page-objects/alert-page';

export default class CountryUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('opusWebApp.country.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  nameInput: ElementFinder = element(by.css('input#country-name'));

  codeInput: ElementFinder = element(by.css('input#country-code'));

  currencySelect = element(by.css('select#country-currency'));
}

import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../page-objects/alert-page';

export default class CCountryUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('opusWebApp.cCountry.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  nameInput: ElementFinder = element(by.css('input#c-country-name'));

  codeInput: ElementFinder = element(by.css('input#c-country-code'));

  withRegionInput: ElementFinder = element(by.css('input#c-country-withRegion'));

  activeInput: ElementFinder = element(by.css('input#c-country-active'));
  currencySelect = element(by.css('select#c-country-currency'));
}

import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../page-objects/alert-page';

export default class CCityUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('opusWebApp.cCity.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  nameInput: ElementFinder = element(by.css('input#c-city-name'));

  activeInput: ElementFinder = element(by.css('input#c-city-active'));
  countrySelect = element(by.css('select#c-city-country'));

  regionSelect = element(by.css('select#c-city-region'));
}

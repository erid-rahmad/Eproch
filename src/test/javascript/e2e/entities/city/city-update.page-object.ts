import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../page-objects/alert-page';

export default class CityUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('opusWebApp.city.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  nameInput: ElementFinder = element(by.css('input#city-name'));

  countrySelect = element(by.css('select#city-country'));

  regionSelect = element(by.css('select#city-region'));
}

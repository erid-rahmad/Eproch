import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../page-objects/alert-page';

export default class RegionUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('opusWebApp.region.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  nameInput: ElementFinder = element(by.css('input#region-name'));

  countrySelect = element(by.css('select#region-country'));
}

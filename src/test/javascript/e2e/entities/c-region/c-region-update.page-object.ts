import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../page-objects/alert-page';

export default class CRegionUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('opusWebApp.cRegion.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  nameInput: ElementFinder = element(by.css('input#c-region-name'));

  activeInput: ElementFinder = element(by.css('input#c-region-active'));
  countrySelect = element(by.css('select#c-region-country'));
}

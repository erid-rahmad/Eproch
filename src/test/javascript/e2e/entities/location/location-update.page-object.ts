import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../page-objects/alert-page';

export default class LocationUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('opusWebApp.location.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  streetAddressInput: ElementFinder = element(by.css('input#location-streetAddress'));

  postalCodeInput: ElementFinder = element(by.css('input#location-postalCode'));

  citySelect = element(by.css('select#location-city'));

  vendorSelect = element(by.css('select#location-vendor'));
}

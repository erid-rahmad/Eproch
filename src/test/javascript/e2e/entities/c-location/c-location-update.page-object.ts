import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../page-objects/alert-page';

export default class CLocationUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('opusWebApp.cLocation.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  streetAddressInput: ElementFinder = element(by.css('input#c-location-streetAddress'));

  postalCodeInput: ElementFinder = element(by.css('input#c-location-postalCode'));

  taxInvoiceAddressInput: ElementFinder = element(by.css('input#c-location-taxInvoiceAddress'));

  activeInput: ElementFinder = element(by.css('input#c-location-active'));
  citySelect = element(by.css('select#c-location-city'));
}

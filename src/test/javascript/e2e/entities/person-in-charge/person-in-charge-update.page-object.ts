import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../page-objects/alert-page';

export default class PersonInChargeUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('opusWebApp.personInCharge.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  positionInput: ElementFinder = element(by.css('input#person-in-charge-position'));

  phoneInput: ElementFinder = element(by.css('input#person-in-charge-phone'));

  userSelect = element(by.css('select#person-in-charge-user'));

  vendorSelect = element(by.css('select#person-in-charge-vendor'));
}

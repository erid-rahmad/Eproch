import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../page-objects/alert-page';

export default class CompanyFunctionaryUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('opusWebApp.companyFunctionary.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  nameInput: ElementFinder = element(by.css('input#company-functionary-name'));

  positionInput: ElementFinder = element(by.css('input#company-functionary-position'));

  phoneInput: ElementFinder = element(by.css('input#company-functionary-phone'));

  emailInput: ElementFinder = element(by.css('input#company-functionary-email'));

  vendorSelect = element(by.css('select#company-functionary-vendor'));
}

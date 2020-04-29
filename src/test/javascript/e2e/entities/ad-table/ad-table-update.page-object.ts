import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../page-objects/alert-page';

export default class ADTableUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('opusWebApp.aDTable.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  nameInput: ElementFinder = element(by.css('input#ad-table-name'));

  viewInput: ElementFinder = element(by.css('input#ad-table-view'));

  activeInput: ElementFinder = element(by.css('input#ad-table-active'));
  adClientSelect = element(by.css('select#ad-table-adClient'));

  adOrganizationSelect = element(by.css('select#ad-table-adOrganization'));
}

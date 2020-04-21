import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../page-objects/alert-page';

export default class ADOrganizationUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('opusWebApp.aDOrganization.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  nameInput: ElementFinder = element(by.css('input#ad-organization-name'));

  codeInput: ElementFinder = element(by.css('input#ad-organization-code'));

  descriptionInput: ElementFinder = element(by.css('input#ad-organization-description'));

  activeInput: ElementFinder = element(by.css('input#ad-organization-active'));
  clientSelect = element(by.css('select#ad-organization-client'));
}

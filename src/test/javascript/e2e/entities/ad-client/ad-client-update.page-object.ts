import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../page-objects/alert-page';

export default class ADClientUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('opusWebApp.aDClient.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  nameInput: ElementFinder = element(by.css('input#ad-client-name'));

  codeInput: ElementFinder = element(by.css('input#ad-client-code'));

  descriptionInput: ElementFinder = element(by.css('input#ad-client-description'));

  activeInput: ElementFinder = element(by.css('input#ad-client-active'));
}

import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../page-objects/alert-page';

export default class PermissionUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('opusWebApp.permission.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  nameInput: ElementFinder = element(by.css('input#permission-name'));

  moduleInput: ElementFinder = element(by.css('input#permission-module'));

  canWriteInput: ElementFinder = element(by.css('input#permission-canWrite'));
}

import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../page-objects/alert-page';

export default class ADWindowUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('opusWebApp.aDWindow.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  nameInput: ElementFinder = element(by.css('input#ad-window-name'));

  descriptionInput: ElementFinder = element(by.css('input#ad-window-description'));

  titleLogicInput: ElementFinder = element(by.css('input#ad-window-titleLogic'));

  typeSelect = element(by.css('select#ad-window-type'));

  activeInput: ElementFinder = element(by.css('input#ad-window-active'));
  adOrganizationSelect = element(by.css('select#ad-window-adOrganization'));
}

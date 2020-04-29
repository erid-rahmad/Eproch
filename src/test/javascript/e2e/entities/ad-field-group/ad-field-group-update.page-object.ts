import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../page-objects/alert-page';

export default class ADFieldGroupUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('opusWebApp.aDFieldGroup.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  nameInput: ElementFinder = element(by.css('input#ad-field-group-name'));

  collapsibleInput: ElementFinder = element(by.css('input#ad-field-group-collapsible'));

  collapseByDefaultInput: ElementFinder = element(by.css('input#ad-field-group-collapseByDefault'));

  activeInput: ElementFinder = element(by.css('input#ad-field-group-active'));
}

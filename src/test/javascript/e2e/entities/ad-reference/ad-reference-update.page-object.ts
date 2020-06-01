import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../page-objects/alert-page';

export default class ADReferenceUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('opusWebApp.aDReference.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  nameInput: ElementFinder = element(by.css('input#ad-reference-name'));

  valueInput: ElementFinder = element(by.css('input#ad-reference-value'));

  descriptionInput: ElementFinder = element(by.css('input#ad-reference-description'));

  referenceTypeSelect = element(by.css('select#ad-reference-referenceType'));

  activeInput: ElementFinder = element(by.css('input#ad-reference-active'));
  adOrganizationSelect = element(by.css('select#ad-reference-adOrganization'));
}

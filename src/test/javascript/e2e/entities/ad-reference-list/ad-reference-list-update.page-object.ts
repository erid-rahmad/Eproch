import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../page-objects/alert-page';

export default class ADReferenceListUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('opusWebApp.aDReferenceList.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  nameInput: ElementFinder = element(by.css('input#ad-reference-list-name'));

  valueInput: ElementFinder = element(by.css('input#ad-reference-list-value'));

  descriptionInput: ElementFinder = element(by.css('input#ad-reference-list-description'));

  activeInput: ElementFinder = element(by.css('input#ad-reference-list-active'));
  adOrganizationSelect = element(by.css('select#ad-reference-list-adOrganization'));

  adReferenceSelect = element(by.css('select#ad-reference-list-adReference'));
}

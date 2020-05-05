import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../page-objects/alert-page';

export default class ADFieldUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('opusWebApp.aDField.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  nameInput: ElementFinder = element(by.css('input#ad-field-name'));

  descriptionInput: ElementFinder = element(by.css('input#ad-field-description'));

  hintInput: ElementFinder = element(by.css('input#ad-field-hint'));

  staticTextInput: ElementFinder = element(by.css('input#ad-field-staticText'));

  staticFieldInput: ElementFinder = element(by.css('input#ad-field-staticField'));

  labelOnlyInput: ElementFinder = element(by.css('input#ad-field-labelOnly'));

  showLabelInput: ElementFinder = element(by.css('input#ad-field-showLabel'));

  showInGridInput: ElementFinder = element(by.css('input#ad-field-showInGrid'));

  showInDetailInput: ElementFinder = element(by.css('input#ad-field-showInDetail'));

  gridSequenceInput: ElementFinder = element(by.css('input#ad-field-gridSequence'));

  detailSequenceInput: ElementFinder = element(by.css('input#ad-field-detailSequence'));

  displayLogicInput: ElementFinder = element(by.css('input#ad-field-displayLogic'));

  writableInput: ElementFinder = element(by.css('input#ad-field-writable'));

  columnNoInput: ElementFinder = element(by.css('input#ad-field-columnNo'));

  columnSpanInput: ElementFinder = element(by.css('input#ad-field-columnSpan'));

  activeInput: ElementFinder = element(by.css('input#ad-field-active'));
  adClientSelect = element(by.css('select#ad-field-adClient'));

  adOrganizationSelect = element(by.css('select#ad-field-adOrganization'));

  adReferenceSelect = element(by.css('select#ad-field-adReference'));

  adTabSelect = element(by.css('select#ad-field-adTab'));
}

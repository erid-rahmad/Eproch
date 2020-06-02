import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../page-objects/alert-page';

export default class ADTabUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('opusWebApp.aDTab.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  nameInput: ElementFinder = element(by.css('input#ad-tab-name'));

  descriptionInput: ElementFinder = element(by.css('input#ad-tab-description'));

  iconNameInput: ElementFinder = element(by.css('input#ad-tab-iconName'));

  targetEndpointInput: ElementFinder = element(by.css('input#ad-tab-targetEndpoint'));

  writableInput: ElementFinder = element(by.css('input#ad-tab-writable'));

  displayLogicInput: ElementFinder = element(by.css('input#ad-tab-displayLogic'));

  readOnlyLogicInput: ElementFinder = element(by.css('input#ad-tab-readOnlyLogic'));

  filterQueryInput: ElementFinder = element(by.css('input#ad-tab-filterQuery'));

  orderQueryInput: ElementFinder = element(by.css('input#ad-tab-orderQuery'));

  tabSequenceInput: ElementFinder = element(by.css('input#ad-tab-tabSequence'));

  activeInput: ElementFinder = element(by.css('input#ad-tab-active'));
  adOrganizationSelect = element(by.css('select#ad-tab-adOrganization'));

  adTableSelect = element(by.css('select#ad-tab-adTable'));

  parentColumnSelect = element(by.css('select#ad-tab-parentColumn'));

  foreignColumnSelect = element(by.css('select#ad-tab-foreignColumn'));

  adWindowSelect = element(by.css('select#ad-tab-adWindow'));

  parentTabSelect = element(by.css('select#ad-tab-parentTab'));
}

import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../page-objects/alert-page';

export default class AdValidationRuleUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('opusWebApp.adValidationRule.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  uidInput: ElementFinder = element(by.css('input#ad-validation-rule-uid'));

  nameInput: ElementFinder = element(by.css('input#ad-validation-rule-name'));

  descriptionInput: ElementFinder = element(by.css('input#ad-validation-rule-description'));

  queryInput: ElementFinder = element(by.css('input#ad-validation-rule-query'));

  activeInput: ElementFinder = element(by.css('input#ad-validation-rule-active'));
  adOrganizationSelect = element(by.css('select#ad-validation-rule-adOrganization'));
}

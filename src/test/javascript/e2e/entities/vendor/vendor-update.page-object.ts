import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../page-objects/alert-page';

export default class VendorUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('opusWebApp.vendor.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  codeInput: ElementFinder = element(by.css('input#vendor-code'));

  nameInput: ElementFinder = element(by.css('input#vendor-name'));

  npwpInput: ElementFinder = element(by.css('input#vendor-npwp'));

  branchInput: ElementFinder = element(by.css('input#vendor-branch'));

  emailInput: ElementFinder = element(by.css('input#vendor-email'));

  phoneInput: ElementFinder = element(by.css('input#vendor-phone'));

  faxInput: ElementFinder = element(by.css('input#vendor-fax'));

  websiteInput: ElementFinder = element(by.css('input#vendor-website'));

  typeSelect = element(by.css('select#vendor-type'));

  paymentCategorySelect = element(by.css('select#vendor-paymentCategory'));

  approvalStatusSelect = element(by.css('select#vendor-approvalStatus'));
  businessCategorySelect = element(by.css('select#vendor-businessCategory'));
}

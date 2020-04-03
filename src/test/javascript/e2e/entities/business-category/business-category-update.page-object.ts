import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../page-objects/alert-page';

export default class BusinessCategoryUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('opusWebApp.businessCategory.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  nameInput: ElementFinder = element(by.css('input#business-category-name'));

  descriptionInput: ElementFinder = element(by.css('input#business-category-description'));

  parentCategorySelect = element(by.css('select#business-category-parentCategory'));
}

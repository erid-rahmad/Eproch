import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../page-objects/alert-page';

export default class CGalleryUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('opusWebApp.cGallery.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  nameInput: ElementFinder = element(by.css('input#c-gallery-name'));

  descriptionInput: ElementFinder = element(by.css('input#c-gallery-description'));

  uidInput: ElementFinder = element(by.css('input#c-gallery-uid'));

  activeInput: ElementFinder = element(by.css('input#c-gallery-active'));
  adOrganizationSelect = element(by.css('select#c-gallery-adOrganization'));
}

import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../page-objects/alert-page';

export default class ADColumnUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('opusWebApp.aDColumn.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  nameInput: ElementFinder = element(by.css('input#ad-column-name'));

  sqlNameInput: ElementFinder = element(by.css('input#ad-column-sqlName'));

  descriptionInput: ElementFinder = element(by.css('input#ad-column-description'));

  fieldLengthInput: ElementFinder = element(by.css('input#ad-column-fieldLength'));

  keyInput: ElementFinder = element(by.css('input#ad-column-key'));

  typeSelect = element(by.css('select#ad-column-type'));

  foreignKeyInput: ElementFinder = element(by.css('input#ad-column-foreignKey'));

  importedTableInput: ElementFinder = element(by.css('input#ad-column-importedTable'));

  importedColumnInput: ElementFinder = element(by.css('input#ad-column-importedColumn'));

  mandatoryInput: ElementFinder = element(by.css('input#ad-column-mandatory'));

  mandatoryLogicInput: ElementFinder = element(by.css('input#ad-column-mandatoryLogic'));

  displayLogicInput: ElementFinder = element(by.css('input#ad-column-displayLogic'));

  readOnlyLogicInput: ElementFinder = element(by.css('input#ad-column-readOnlyLogic'));

  updatableInput: ElementFinder = element(by.css('input#ad-column-updatable'));

  alwaysUpdatableInput: ElementFinder = element(by.css('input#ad-column-alwaysUpdatable'));

  copyableInput: ElementFinder = element(by.css('input#ad-column-copyable'));

  defaultValueInput: ElementFinder = element(by.css('input#ad-column-defaultValue'));

  formatPatternInput: ElementFinder = element(by.css('input#ad-column-formatPattern'));

  minLengthInput: ElementFinder = element(by.css('input#ad-column-minLength'));

  maxLengthInput: ElementFinder = element(by.css('input#ad-column-maxLength'));

  minValueInput: ElementFinder = element(by.css('input#ad-column-minValue'));

  maxValueInput: ElementFinder = element(by.css('input#ad-column-maxValue'));

  identifierInput: ElementFinder = element(by.css('input#ad-column-identifier'));

  defaultSelectionInput: ElementFinder = element(by.css('input#ad-column-defaultSelection'));

  selectionSequenceInput: ElementFinder = element(by.css('input#ad-column-selectionSequence'));

  activeInput: ElementFinder = element(by.css('input#ad-column-active'));
  adOrganizationSelect = element(by.css('select#ad-column-adOrganization'));

  adReferenceSelect = element(by.css('select#ad-column-adReference'));

  adValidationRuleSelect = element(by.css('select#ad-column-adValidationRule'));

  adTableSelect = element(by.css('select#ad-column-adTable'));
}

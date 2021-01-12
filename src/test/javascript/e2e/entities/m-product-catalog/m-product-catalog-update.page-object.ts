import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../page-objects/alert-page';

export default class MProductCatalogUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('opusWebApp.mProductCatalog.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  nameInput: ElementFinder = element(by.css('input#m-product-catalog-name'));

  descriptionInput: ElementFinder = element(by.css('input#m-product-catalog-description'));

  shortDescriptionInput: ElementFinder = element(by.css('input#m-product-catalog-shortDescription'));

  heightInput: ElementFinder = element(by.css('input#m-product-catalog-height'));

  lengthInput: ElementFinder = element(by.css('input#m-product-catalog-length'));

  widthInput: ElementFinder = element(by.css('input#m-product-catalog-width'));

  weightInput: ElementFinder = element(by.css('input#m-product-catalog-weight'));

  priceInput: ElementFinder = element(by.css('input#m-product-catalog-price'));

  expiredDateInput: ElementFinder = element(by.css('input#m-product-catalog-expiredDate'));

  documentActionInput: ElementFinder = element(by.css('input#m-product-catalog-documentAction'));

  documentStatusInput: ElementFinder = element(by.css('input#m-product-catalog-documentStatus'));

  approvedInput: ElementFinder = element(by.css('input#m-product-catalog-approved'));

  processedInput: ElementFinder = element(by.css('input#m-product-catalog-processed'));

  rejectedReasonInput: ElementFinder = element(by.css('input#m-product-catalog-rejectedReason'));

  uidInput: ElementFinder = element(by.css('input#m-product-catalog-uid'));

  activeInput: ElementFinder = element(by.css('input#m-product-catalog-active'));
  cGallerySelect = element(by.css('select#m-product-catalog-cGallery'));

  adOrganizationSelect = element(by.css('select#m-product-catalog-adOrganization'));

  cDocumentTypeSelect = element(by.css('select#m-product-catalog-cDocumentType'));

  cCurrencySelect = element(by.css('select#m-product-catalog-cCurrency'));

  cUomSelect = element(by.css('select#m-product-catalog-cUom'));

  cVendorSelect = element(by.css('select#m-product-catalog-cVendor'));

  mBrandSelect = element(by.css('select#m-product-catalog-mBrand'));

  mProductSelect = element(by.css('select#m-product-catalog-mProduct'));
}

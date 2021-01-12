/* tslint:disable no-unused-expression */
import { browser } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import MProductCatalogComponentsPage, { MProductCatalogDeleteDialog } from './m-product-catalog.page-object';
import MProductCatalogUpdatePage from './m-product-catalog-update.page-object';
import MProductCatalogDetailsPage from './m-product-catalog-details.page-object';

import {
  clear,
  click,
  getRecordsCount,
  isVisible,
  selectLastOption,
  waitUntilAllDisplayed,
  waitUntilAnyDisplayed,
  waitUntilCount,
  waitUntilDisplayed,
  waitUntilHidden
} from '../../util/utils';

const expect = chai.expect;

describe('MProductCatalog e2e test', () => {
  let navBarPage: NavBarPage;
  let updatePage: MProductCatalogUpdatePage;
  let detailsPage: MProductCatalogDetailsPage;
  let listPage: MProductCatalogComponentsPage;
  /*let deleteDialog: MProductCatalogDeleteDialog;*/
  let beforeRecordsCount = 0;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    await navBarPage.login('admin', 'admin');
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });

  it('should load MProductCatalogs', async () => {
    await navBarPage.getEntityPage('m-product-catalog');
    listPage = new MProductCatalogComponentsPage();

    await waitUntilAllDisplayed([listPage.title, listPage.footer]);

    expect(await listPage.title.getText()).not.to.be.empty;
    expect(await listPage.createButton.isEnabled()).to.be.true;

    await waitUntilAnyDisplayed([listPage.noRecords, listPage.table]);
    beforeRecordsCount = (await isVisible(listPage.noRecords)) ? 0 : await getRecordsCount(listPage.table);
  });
  describe('Create flow', () => {
    it('should load create MProductCatalog page', async () => {
      await listPage.createButton.click();
      updatePage = new MProductCatalogUpdatePage();

      await waitUntilAllDisplayed([updatePage.title, updatePage.footer, updatePage.saveButton]);

      expect(await updatePage.title.getAttribute('id')).to.match(/opusWebApp.mProductCatalog.home.createOrEditLabel/);
    });

    /* it('should create and save MProductCatalogs', async () => {

      await updatePage.nameInput.sendKeys('name');
      expect(await updatePage.nameInput.getAttribute('value')).to.match(/name/);


      await updatePage.descriptionInput.sendKeys('description');
      expect(await updatePage.descriptionInput.getAttribute('value')).to.match(/description/);


      await updatePage.shortDescriptionInput.sendKeys('shortDescription');
      expect(await updatePage.shortDescriptionInput.getAttribute('value')).to.match(/shortDescription/);


      await updatePage.heightInput.sendKeys('5');
      expect(await updatePage.heightInput.getAttribute('value')).to.eq('5');


      await updatePage.lengthInput.sendKeys('5');
      expect(await updatePage.lengthInput.getAttribute('value')).to.eq('5');


      await updatePage.widthInput.sendKeys('5');
      expect(await updatePage.widthInput.getAttribute('value')).to.eq('5');


      await updatePage.weightInput.sendKeys('5');
      expect(await updatePage.weightInput.getAttribute('value')).to.eq('5');


      await updatePage.priceInput.sendKeys('5');
      expect(await updatePage.priceInput.getAttribute('value')).to.eq('5');


      await updatePage.expiredDateInput.sendKeys('01-01-2001');
      expect(await updatePage.expiredDateInput.getAttribute('value')).to.eq('2001-01-01');


      await updatePage.documentActionInput.sendKeys('documentAction');
      expect(await updatePage.documentActionInput.getAttribute('value')).to.match(/documentAction/);


      await updatePage.documentStatusInput.sendKeys('documentStatus');
      expect(await updatePage.documentStatusInput.getAttribute('value')).to.match(/documentStatus/);


      const selectedApproved = await updatePage.approvedInput.isSelected();
      if (selectedApproved) {
        await updatePage.approvedInput.click();
        expect(await updatePage.approvedInput.isSelected()).to.be.false;
      } else {
        await updatePage.approvedInput.click();
        expect(await updatePage.approvedInput.isSelected()).to.be.true;
      }


      const selectedProcessed = await updatePage.processedInput.isSelected();
      if (selectedProcessed) {
        await updatePage.processedInput.click();
        expect(await updatePage.processedInput.isSelected()).to.be.false;
      } else {
        await updatePage.processedInput.click();
        expect(await updatePage.processedInput.isSelected()).to.be.true;
      }


      await updatePage.rejectedReasonInput.sendKeys('rejectedReason');
      expect(await updatePage.rejectedReasonInput.getAttribute('value')).to.match(/rejectedReason/);


      await updatePage.uidInput.sendKeys('64c99148-3908-465d-8c4a-e510e3ade974');
      expect(await updatePage.uidInput.getAttribute('value')).to.match(/64c99148-3908-465d-8c4a-e510e3ade974/);


      const selectedActive = await updatePage.activeInput.isSelected();
      if (selectedActive) {
        await updatePage.activeInput.click();
        expect(await updatePage.activeInput.isSelected()).to.be.false;
      } else {
        await updatePage.activeInput.click();
        expect(await updatePage.activeInput.isSelected()).to.be.true;
      }

      // await  selectLastOption(updatePage.cGallerySelect);
      // await  selectLastOption(updatePage.adOrganizationSelect);
      // await  selectLastOption(updatePage.cDocumentTypeSelect);
      // await  selectLastOption(updatePage.cCurrencySelect);
      // await  selectLastOption(updatePage.cUomSelect);
      // await  selectLastOption(updatePage.cVendorSelect);
      // await  selectLastOption(updatePage.mBrandSelect);
      // await  selectLastOption(updatePage.mProductSelect);

      expect(await updatePage.saveButton.isEnabled()).to.be.true;
      await updatePage.saveButton.click();

      await waitUntilHidden(updatePage.saveButton);
      expect(await isVisible(updatePage.saveButton)).to.be.false;

      await waitUntilDisplayed(listPage.successAlert);
      expect(await listPage.successAlert.isDisplayed()).to.be.true;

      await waitUntilCount(listPage.records, beforeRecordsCount + 1);
      expect(await listPage.records.count()).to.eq(beforeRecordsCount + 1);
    });*/

    /*
    describe('Details, Update, Delete flow', () => {

      after(async () => {

        const deleteButton = listPage.getDeleteButton(listPage.records.first());
        await click(deleteButton);

        deleteDialog = new MProductCatalogDeleteDialog();
        await waitUntilDisplayed(deleteDialog.dialog);

        expect(await deleteDialog.title.getAttribute('id')).to.match(/opusWebApp.mProductCatalog.delete.question/);

        await click(deleteDialog.confirmButton);
        await waitUntilHidden(deleteDialog.dialog);

        expect(await isVisible(deleteDialog.dialog)).to.be.false;
        expect(await listPage.dangerAlert.isDisplayed()).to.be.true;

        await waitUntilCount(listPage.records, beforeRecordsCount);
        expect(await listPage.records.count()).to.eq(beforeRecordsCount);
      });

      it('should load details MProductCatalog page and fetch data', async () => {

        const detailsButton = listPage.getDetailsButton(listPage.records.first());
        await click(detailsButton);

        detailsPage = new MProductCatalogDetailsPage();

        await waitUntilAllDisplayed([detailsPage.title, detailsPage.backButton, detailsPage.firstDetail]);

        expect(await detailsPage.title.getText()).not.to.be.empty;
        expect(await detailsPage.firstDetail.getText()).not.to.be.empty;

        await click(detailsPage.backButton);
        await waitUntilCount(listPage.records, beforeRecordsCount + 1);
      });

      it('should load edit MProductCatalog page, fetch data and update', async () => {

        const editButton = listPage.getEditButton(listPage.records.first());
        await click(editButton);

        await waitUntilAllDisplayed([updatePage.title, updatePage.footer, updatePage.saveButton]);

        expect(await updatePage.title.getText()).not.to.be.empty;

          await updatePage.nameInput.clear();
          await updatePage.nameInput.sendKeys('modified');
          expect(await updatePage.nameInput.getAttribute('value')).to.match(/modified/);

          await updatePage.descriptionInput.clear();
          await updatePage.descriptionInput.sendKeys('modified');
          expect(await updatePage.descriptionInput.getAttribute('value')).to.match(/modified/);

          await updatePage.shortDescriptionInput.clear();
          await updatePage.shortDescriptionInput.sendKeys('modified');
          expect(await updatePage.shortDescriptionInput.getAttribute('value')).to.match(/modified/);

          await clear(updatePage.heightInput);
          await updatePage.heightInput.sendKeys('6');
          expect(await updatePage.heightInput.getAttribute('value')).to.eq('6');

          await clear(updatePage.lengthInput);
          await updatePage.lengthInput.sendKeys('6');
          expect(await updatePage.lengthInput.getAttribute('value')).to.eq('6');

          await clear(updatePage.widthInput);
          await updatePage.widthInput.sendKeys('6');
          expect(await updatePage.widthInput.getAttribute('value')).to.eq('6');

          await clear(updatePage.weightInput);
          await updatePage.weightInput.sendKeys('6');
          expect(await updatePage.weightInput.getAttribute('value')).to.eq('6');

          await clear(updatePage.priceInput);
          await updatePage.priceInput.sendKeys('6');
          expect(await updatePage.priceInput.getAttribute('value')).to.eq('6');

          await updatePage.expiredDateInput.clear();
          await updatePage.expiredDateInput.sendKeys('01-01-2019');
          expect(await updatePage.expiredDateInput.getAttribute('value')).to.eq('2019-01-01');

          await updatePage.documentActionInput.clear();
          await updatePage.documentActionInput.sendKeys('modified');
          expect(await updatePage.documentActionInput.getAttribute('value')).to.match(/modified/);

          await updatePage.documentStatusInput.clear();
          await updatePage.documentStatusInput.sendKeys('modified');
          expect(await updatePage.documentStatusInput.getAttribute('value')).to.match(/modified/);

          const selectedApproved = await updatePage.approvedInput.isSelected();
          if (selectedApproved) {
            await updatePage.approvedInput.click();
            expect(await updatePage.approvedInput.isSelected()).to.be.false;
          } else {
            await updatePage.approvedInput.click();
            expect(await updatePage.approvedInput.isSelected()).to.be.true;
          }

          const selectedProcessed = await updatePage.processedInput.isSelected();
          if (selectedProcessed) {
            await updatePage.processedInput.click();
            expect(await updatePage.processedInput.isSelected()).to.be.false;
          } else {
            await updatePage.processedInput.click();
            expect(await updatePage.processedInput.isSelected()).to.be.true;
          }

          await updatePage.rejectedReasonInput.clear();
          await updatePage.rejectedReasonInput.sendKeys('modified');
          expect(await updatePage.rejectedReasonInput.getAttribute('value')).to.match(/modified/);

          await updatePage.uidInput.clear();
          await updatePage.uidInput.sendKeys('64c99148-3908-465d-8c4a-e510e3ade978');
          expect(await updatePage.uidInput.getAttribute('value')).to.match(/64c99148-3908-465d-8c4a-e510e3ade978/);

          const selectedActive = await updatePage.activeInput.isSelected();
          if (selectedActive) {
            await updatePage.activeInput.click();
            expect(await updatePage.activeInput.isSelected()).to.be.false;
          } else {
            await updatePage.activeInput.click();
            expect(await updatePage.activeInput.isSelected()).to.be.true;
          }


        await updatePage.saveButton.click();

        await waitUntilHidden(updatePage.saveButton);

        expect(await isVisible(updatePage.saveButton)).to.be.false;
        expect(await listPage.infoAlert.isDisplayed()).to.be.true;
        await waitUntilCount(listPage.records, beforeRecordsCount + 1);
      });
    });
    */
  });
});

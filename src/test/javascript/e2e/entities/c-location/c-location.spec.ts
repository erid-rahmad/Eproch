/* tslint:disable no-unused-expression */
import { browser } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import CLocationComponentsPage, { CLocationDeleteDialog } from './c-location.page-object';
import CLocationUpdatePage from './c-location-update.page-object';
import CLocationDetailsPage from './c-location-details.page-object';

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

describe('CLocation e2e test', () => {
  let navBarPage: NavBarPage;
  let updatePage: CLocationUpdatePage;
  let detailsPage: CLocationDetailsPage;
  let listPage: CLocationComponentsPage;
  /*let deleteDialog: CLocationDeleteDialog;*/
  let beforeRecordsCount = 0;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    await navBarPage.login('admin', 'admin');
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });

  it('should load CLocations', async () => {
    await navBarPage.getEntityPage('c-location');
    listPage = new CLocationComponentsPage();

    await waitUntilAllDisplayed([listPage.title, listPage.footer]);

    expect(await listPage.title.getText()).not.to.be.empty;
    expect(await listPage.createButton.isEnabled()).to.be.true;

    await waitUntilAnyDisplayed([listPage.noRecords, listPage.table]);
    beforeRecordsCount = (await isVisible(listPage.noRecords)) ? 0 : await getRecordsCount(listPage.table);
  });
  describe('Create flow', () => {
    it('should load create CLocation page', async () => {
      await listPage.createButton.click();
      updatePage = new CLocationUpdatePage();

      await waitUntilAllDisplayed([updatePage.title, updatePage.footer, updatePage.saveButton]);

      expect(await updatePage.title.getAttribute('id')).to.match(/opusWebApp.cLocation.home.createOrEditLabel/);
    });

    /* it('should create and save CLocations', async () => {

      await updatePage.streetAddressInput.sendKeys('streetAddress');
      expect(await updatePage.streetAddressInput.getAttribute('value')).to.match(/streetAddress/);


      await updatePage.postalCodeInput.sendKeys('postalCode');
      expect(await updatePage.postalCodeInput.getAttribute('value')).to.match(/postalCode/);


      const selectedTaxInvoiceAddress = await updatePage.taxInvoiceAddressInput.isSelected();
      if (selectedTaxInvoiceAddress) {
        await updatePage.taxInvoiceAddressInput.click();
        expect(await updatePage.taxInvoiceAddressInput.isSelected()).to.be.false;
      } else {
        await updatePage.taxInvoiceAddressInput.click();
        expect(await updatePage.taxInvoiceAddressInput.isSelected()).to.be.true;
      }


      const selectedActive = await updatePage.activeInput.isSelected();
      if (selectedActive) {
        await updatePage.activeInput.click();
        expect(await updatePage.activeInput.isSelected()).to.be.false;
      } else {
        await updatePage.activeInput.click();
        expect(await updatePage.activeInput.isSelected()).to.be.true;
      }

      // await  selectLastOption(updatePage.citySelect);

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

        deleteDialog = new CLocationDeleteDialog();
        await waitUntilDisplayed(deleteDialog.dialog);

        expect(await deleteDialog.title.getAttribute('id')).to.match(/opusWebApp.cLocation.delete.question/);

        await click(deleteDialog.confirmButton);
        await waitUntilHidden(deleteDialog.dialog);

        expect(await isVisible(deleteDialog.dialog)).to.be.false;
        expect(await listPage.dangerAlert.isDisplayed()).to.be.true;

        await waitUntilCount(listPage.records, beforeRecordsCount);
        expect(await listPage.records.count()).to.eq(beforeRecordsCount);
      });

      it('should load details CLocation page and fetch data', async () => {

        const detailsButton = listPage.getDetailsButton(listPage.records.first());
        await click(detailsButton);

        detailsPage = new CLocationDetailsPage();

        await waitUntilAllDisplayed([detailsPage.title, detailsPage.backButton, detailsPage.firstDetail]);

        expect(await detailsPage.title.getText()).not.to.be.empty;
        expect(await detailsPage.firstDetail.getText()).not.to.be.empty;

        await click(detailsPage.backButton);
        await waitUntilCount(listPage.records, beforeRecordsCount + 1);
      });

      it('should load edit CLocation page, fetch data and update', async () => {

        const editButton = listPage.getEditButton(listPage.records.first());
        await click(editButton);

        await waitUntilAllDisplayed([updatePage.title, updatePage.footer, updatePage.saveButton]);

        expect(await updatePage.title.getText()).not.to.be.empty;

          await updatePage.streetAddressInput.clear();
          await updatePage.streetAddressInput.sendKeys('modified');
          expect(await updatePage.streetAddressInput.getAttribute('value')).to.match(/modified/);

          await updatePage.postalCodeInput.clear();
          await updatePage.postalCodeInput.sendKeys('modified');
          expect(await updatePage.postalCodeInput.getAttribute('value')).to.match(/modified/);

          const selectedTaxInvoiceAddress = await updatePage.taxInvoiceAddressInput.isSelected();
          if (selectedTaxInvoiceAddress) {
            await updatePage.taxInvoiceAddressInput.click();
            expect(await updatePage.taxInvoiceAddressInput.isSelected()).to.be.false;
          } else {
            await updatePage.taxInvoiceAddressInput.click();
            expect(await updatePage.taxInvoiceAddressInput.isSelected()).to.be.true;
          }

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

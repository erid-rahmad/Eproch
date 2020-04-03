/* tslint:disable no-unused-expression */
import { browser } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import VendorComponentsPage, { VendorDeleteDialog } from './vendor.page-object';
import VendorUpdatePage from './vendor-update.page-object';
import VendorDetailsPage from './vendor-details.page-object';

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

describe('Vendor e2e test', () => {
  let navBarPage: NavBarPage;
  let updatePage: VendorUpdatePage;
  let detailsPage: VendorDetailsPage;
  let listPage: VendorComponentsPage;
  /*let deleteDialog: VendorDeleteDialog;*/
  let beforeRecordsCount = 0;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    await navBarPage.login('admin', 'admin');
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });

  it('should load Vendors', async () => {
    await navBarPage.getEntityPage('vendor');
    listPage = new VendorComponentsPage();

    await waitUntilAllDisplayed([listPage.title, listPage.footer]);

    expect(await listPage.title.getText()).not.to.be.empty;
    expect(await listPage.createButton.isEnabled()).to.be.true;

    await waitUntilAnyDisplayed([listPage.noRecords, listPage.table]);
    beforeRecordsCount = (await isVisible(listPage.noRecords)) ? 0 : await getRecordsCount(listPage.table);
  });
  describe('Create flow', () => {
    it('should load create Vendor page', async () => {
      await listPage.createButton.click();
      updatePage = new VendorUpdatePage();

      await waitUntilAllDisplayed([updatePage.title, updatePage.footer, updatePage.saveButton]);

      expect(await updatePage.title.getAttribute('id')).to.match(/opusWebApp.vendor.home.createOrEditLabel/);
    });

    /* it('should create and save Vendors', async () => {

      await updatePage.codeInput.sendKeys('code');
      expect(await updatePage.codeInput.getAttribute('value')).to.match(/code/);


      await updatePage.nameInput.sendKeys('name');
      expect(await updatePage.nameInput.getAttribute('value')).to.match(/name/);


      await updatePage.npwpInput.sendKeys('5');
      expect(await updatePage.npwpInput.getAttribute('value')).to.eq('5');


      const selectedBranch = await updatePage.branchInput.isSelected();
      if (selectedBranch) {
        await updatePage.branchInput.click();
        expect(await updatePage.branchInput.isSelected()).to.be.false;
      } else {
        await updatePage.branchInput.click();
        expect(await updatePage.branchInput.isSelected()).to.be.true;
      }


      await updatePage.emailInput.sendKeys('email');
      expect(await updatePage.emailInput.getAttribute('value')).to.match(/email/);


      await updatePage.phoneInput.sendKeys('phone');
      expect(await updatePage.phoneInput.getAttribute('value')).to.match(/phone/);


      await updatePage.faxInput.sendKeys('fax');
      expect(await updatePage.faxInput.getAttribute('value')).to.match(/fax/);


      await updatePage.websiteInput.sendKeys('website');
      expect(await updatePage.websiteInput.getAttribute('value')).to.match(/website/);


      await selectLastOption(updatePage.typeSelect);


      await selectLastOption(updatePage.paymentCategorySelect);


      await selectLastOption(updatePage.approvalStatusSelect);

      // await  selectLastOption(updatePage.locationSelect);
      // await  selectLastOption(updatePage.businessCategorySelect);

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

        deleteDialog = new VendorDeleteDialog();
        await waitUntilDisplayed(deleteDialog.dialog);

        expect(await deleteDialog.title.getAttribute('id')).to.match(/opusWebApp.vendor.delete.question/);

        await click(deleteDialog.confirmButton);
        await waitUntilHidden(deleteDialog.dialog);

        expect(await isVisible(deleteDialog.dialog)).to.be.false;
        expect(await listPage.dangerAlert.isDisplayed()).to.be.true;

        await waitUntilCount(listPage.records, beforeRecordsCount);
        expect(await listPage.records.count()).to.eq(beforeRecordsCount);
      });

      it('should load details Vendor page and fetch data', async () => {

        const detailsButton = listPage.getDetailsButton(listPage.records.first());
        await click(detailsButton);

        detailsPage = new VendorDetailsPage();

        await waitUntilAllDisplayed([detailsPage.title, detailsPage.backButton, detailsPage.firstDetail]);

        expect(await detailsPage.title.getText()).not.to.be.empty;
        expect(await detailsPage.firstDetail.getText()).not.to.be.empty;

        await click(detailsPage.backButton);
        await waitUntilCount(listPage.records, beforeRecordsCount + 1);
      });

      it('should load edit Vendor page, fetch data and update', async () => {

        const editButton = listPage.getEditButton(listPage.records.first());
        await click(editButton);

        await waitUntilAllDisplayed([updatePage.title, updatePage.footer, updatePage.saveButton]);

        expect(await updatePage.title.getText()).not.to.be.empty;

          await updatePage.codeInput.clear();
          await updatePage.codeInput.sendKeys('modified');
          expect(await updatePage.codeInput.getAttribute('value')).to.match(/modified/);

          await updatePage.nameInput.clear();
          await updatePage.nameInput.sendKeys('modified');
          expect(await updatePage.nameInput.getAttribute('value')).to.match(/modified/);

          await clear(updatePage.npwpInput);
          await updatePage.npwpInput.sendKeys('6');
          expect(await updatePage.npwpInput.getAttribute('value')).to.eq('6');

          const selectedBranch = await updatePage.branchInput.isSelected();
          if (selectedBranch) {
            await updatePage.branchInput.click();
            expect(await updatePage.branchInput.isSelected()).to.be.false;
          } else {
            await updatePage.branchInput.click();
            expect(await updatePage.branchInput.isSelected()).to.be.true;
          }

          await updatePage.emailInput.clear();
          await updatePage.emailInput.sendKeys('modified');
          expect(await updatePage.emailInput.getAttribute('value')).to.match(/modified/);

          await updatePage.phoneInput.clear();
          await updatePage.phoneInput.sendKeys('modified');
          expect(await updatePage.phoneInput.getAttribute('value')).to.match(/modified/);

          await updatePage.faxInput.clear();
          await updatePage.faxInput.sendKeys('modified');
          expect(await updatePage.faxInput.getAttribute('value')).to.match(/modified/);

          await updatePage.websiteInput.clear();
          await updatePage.websiteInput.sendKeys('modified');
          expect(await updatePage.websiteInput.getAttribute('value')).to.match(/modified/);


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

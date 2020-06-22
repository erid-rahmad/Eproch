/* tslint:disable no-unused-expression */
import { browser } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import CBankComponentsPage, { CBankDeleteDialog } from './c-bank.page-object';
import CBankUpdatePage from './c-bank-update.page-object';
import CBankDetailsPage from './c-bank-details.page-object';

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

describe('CBank e2e test', () => {
  let navBarPage: NavBarPage;
  let updatePage: CBankUpdatePage;
  let detailsPage: CBankDetailsPage;
  let listPage: CBankComponentsPage;
  let deleteDialog: CBankDeleteDialog;
  let beforeRecordsCount = 0;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    await navBarPage.login('admin', 'admin');
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });

  it('should load CBanks', async () => {
    await navBarPage.getEntityPage('c-bank');
    listPage = new CBankComponentsPage();

    await waitUntilAllDisplayed([listPage.title, listPage.footer]);

    expect(await listPage.title.getText()).not.to.be.empty;
    expect(await listPage.createButton.isEnabled()).to.be.true;

    await waitUntilAnyDisplayed([listPage.noRecords, listPage.table]);
    beforeRecordsCount = (await isVisible(listPage.noRecords)) ? 0 : await getRecordsCount(listPage.table);
  });
  describe('Create flow', () => {
    it('should load create CBank page', async () => {
      await listPage.createButton.click();
      updatePage = new CBankUpdatePage();

      await waitUntilAllDisplayed([updatePage.title, updatePage.footer, updatePage.saveButton]);

      expect(await updatePage.title.getAttribute('id')).to.match(/opusWebApp.cBank.home.createOrEditLabel/);
    });

    it('should create and save CBanks', async () => {
      await updatePage.nameInput.sendKeys('name');
      expect(await updatePage.nameInput.getAttribute('value')).to.match(/name/);

      await updatePage.valueInput.sendKeys('value');
      expect(await updatePage.valueInput.getAttribute('value')).to.match(/value/);

      await updatePage.shortNameInput.sendKeys('shortName');
      expect(await updatePage.shortNameInput.getAttribute('value')).to.match(/shortName/);

      await updatePage.descriptionInput.sendKeys('description');
      expect(await updatePage.descriptionInput.getAttribute('value')).to.match(/description/);

      await updatePage.swiftCodeInput.sendKeys('EISYBA58');
      expect(await updatePage.swiftCodeInput.getAttribute('value')).to.eq('EISYBA58');

      const selectedActive = await updatePage.activeInput.isSelected();
      if (selectedActive) {
        await updatePage.activeInput.click();
        expect(await updatePage.activeInput.isSelected()).to.be.false;
      } else {
        await updatePage.activeInput.click();
        expect(await updatePage.activeInput.isSelected()).to.be.true;
      }

      expect(await updatePage.saveButton.isEnabled()).to.be.true;
      await updatePage.saveButton.click();

      await waitUntilHidden(updatePage.saveButton);
      expect(await isVisible(updatePage.saveButton)).to.be.false;

      await waitUntilDisplayed(listPage.successAlert);
      expect(await listPage.successAlert.isDisplayed()).to.be.true;

      await waitUntilCount(listPage.records, beforeRecordsCount + 1);
      expect(await listPage.records.count()).to.eq(beforeRecordsCount + 1);
    });

    describe('Details, Update, Delete flow', () => {
      after(async () => {
        const deleteButton = listPage.getDeleteButton(listPage.records.first());
        await click(deleteButton);

        deleteDialog = new CBankDeleteDialog();
        await waitUntilDisplayed(deleteDialog.dialog);

        expect(await deleteDialog.title.getAttribute('id')).to.match(/opusWebApp.cBank.delete.question/);

        await click(deleteDialog.confirmButton);
        await waitUntilHidden(deleteDialog.dialog);

        expect(await isVisible(deleteDialog.dialog)).to.be.false;
        expect(await listPage.dangerAlert.isDisplayed()).to.be.true;

        await waitUntilCount(listPage.records, beforeRecordsCount);
        expect(await listPage.records.count()).to.eq(beforeRecordsCount);
      });

      it('should load details CBank page and fetch data', async () => {
        const detailsButton = listPage.getDetailsButton(listPage.records.first());
        await click(detailsButton);

        detailsPage = new CBankDetailsPage();

        await waitUntilAllDisplayed([detailsPage.title, detailsPage.backButton, detailsPage.firstDetail]);

        expect(await detailsPage.title.getText()).not.to.be.empty;
        expect(await detailsPage.firstDetail.getText()).not.to.be.empty;

        await click(detailsPage.backButton);
        await waitUntilCount(listPage.records, beforeRecordsCount + 1);
      });

      it('should load edit CBank page, fetch data and update', async () => {
        const editButton = listPage.getEditButton(listPage.records.first());
        await click(editButton);

        await waitUntilAllDisplayed([updatePage.title, updatePage.footer, updatePage.saveButton]);

        expect(await updatePage.title.getText()).not.to.be.empty;

        await updatePage.nameInput.clear();
        await updatePage.nameInput.sendKeys('modified');
        expect(await updatePage.nameInput.getAttribute('value')).to.match(/modified/);

        await updatePage.valueInput.clear();
        await updatePage.valueInput.sendKeys('modified');
        expect(await updatePage.valueInput.getAttribute('value')).to.match(/modified/);

        await updatePage.shortNameInput.clear();
        await updatePage.shortNameInput.sendKeys('modified');
        expect(await updatePage.shortNameInput.getAttribute('value')).to.match(/modified/);

        await updatePage.descriptionInput.clear();
        await updatePage.descriptionInput.sendKeys('modified');
        expect(await updatePage.descriptionInput.getAttribute('value')).to.match(/modified/);

        await updatePage.swiftCodeInput.clear();
        await updatePage.swiftCodeInput.sendKeys('ULEDZV7XRWA');
        expect(await updatePage.swiftCodeInput.getAttribute('value')).to.eq('ULEDZV7XRWA');

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
  });
});

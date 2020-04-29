/* tslint:disable no-unused-expression */
import { browser } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import ADTabComponentsPage, { ADTabDeleteDialog } from './ad-tab.page-object';
import ADTabUpdatePage from './ad-tab-update.page-object';
import ADTabDetailsPage from './ad-tab-details.page-object';

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

describe('ADTab e2e test', () => {
  let navBarPage: NavBarPage;
  let updatePage: ADTabUpdatePage;
  let detailsPage: ADTabDetailsPage;
  let listPage: ADTabComponentsPage;
  /*let deleteDialog: ADTabDeleteDialog;*/
  let beforeRecordsCount = 0;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    await navBarPage.login('admin', 'admin');
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });

  it('should load ADTabs', async () => {
    await navBarPage.getEntityPage('ad-tab');
    listPage = new ADTabComponentsPage();

    await waitUntilAllDisplayed([listPage.title, listPage.footer]);

    expect(await listPage.title.getText()).not.to.be.empty;
    expect(await listPage.createButton.isEnabled()).to.be.true;

    await waitUntilAnyDisplayed([listPage.noRecords, listPage.table]);
    beforeRecordsCount = (await isVisible(listPage.noRecords)) ? 0 : await getRecordsCount(listPage.table);
  });
  describe('Create flow', () => {
    it('should load create ADTab page', async () => {
      await listPage.createButton.click();
      updatePage = new ADTabUpdatePage();

      await waitUntilAllDisplayed([updatePage.title, updatePage.footer, updatePage.saveButton]);

      expect(await updatePage.title.getAttribute('id')).to.match(/opusWebApp.aDTab.home.createOrEditLabel/);
    });

    /* it('should create and save ADTabs', async () => {

      await updatePage.nameInput.sendKeys('name');
      expect(await updatePage.nameInput.getAttribute('value')).to.match(/name/);


      await updatePage.descriptionInput.sendKeys('description');
      expect(await updatePage.descriptionInput.getAttribute('value')).to.match(/description/);


      await updatePage.targetEndpointInput.sendKeys('targetEndpoint');
      expect(await updatePage.targetEndpointInput.getAttribute('value')).to.match(/targetEndpoint/);


      await updatePage.levelInput.sendKeys('5');
      expect(await updatePage.levelInput.getAttribute('value')).to.eq('5');


      const selectedWritable = await updatePage.writableInput.isSelected();
      if (selectedWritable) {
        await updatePage.writableInput.click();
        expect(await updatePage.writableInput.isSelected()).to.be.false;
      } else {
        await updatePage.writableInput.click();
        expect(await updatePage.writableInput.isSelected()).to.be.true;
      }


      await updatePage.displayLogicInput.sendKeys('displayLogic');
      expect(await updatePage.displayLogicInput.getAttribute('value')).to.match(/displayLogic/);


      await updatePage.readOnlyLogicInput.sendKeys('readOnlyLogic');
      expect(await updatePage.readOnlyLogicInput.getAttribute('value')).to.match(/readOnlyLogic/);


      await updatePage.filterQueryInput.sendKeys('filterQuery');
      expect(await updatePage.filterQueryInput.getAttribute('value')).to.match(/filterQuery/);


      await updatePage.orderQueryInput.sendKeys('orderQuery');
      expect(await updatePage.orderQueryInput.getAttribute('value')).to.match(/orderQuery/);


      const selectedActive = await updatePage.activeInput.isSelected();
      if (selectedActive) {
        await updatePage.activeInput.click();
        expect(await updatePage.activeInput.isSelected()).to.be.false;
      } else {
        await updatePage.activeInput.click();
        expect(await updatePage.activeInput.isSelected()).to.be.true;
      }

      // await  selectLastOption(updatePage.adClientSelect);
      // await  selectLastOption(updatePage.adOrganizationSelect);
      // await  selectLastOption(updatePage.adTableSelect);
      // await  selectLastOption(updatePage.adWindowSelect);

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

        deleteDialog = new ADTabDeleteDialog();
        await waitUntilDisplayed(deleteDialog.dialog);

        expect(await deleteDialog.title.getAttribute('id')).to.match(/opusWebApp.aDTab.delete.question/);

        await click(deleteDialog.confirmButton);
        await waitUntilHidden(deleteDialog.dialog);

        expect(await isVisible(deleteDialog.dialog)).to.be.false;
        expect(await listPage.dangerAlert.isDisplayed()).to.be.true;

        await waitUntilCount(listPage.records, beforeRecordsCount);
        expect(await listPage.records.count()).to.eq(beforeRecordsCount);
      });

      it('should load details ADTab page and fetch data', async () => {

        const detailsButton = listPage.getDetailsButton(listPage.records.first());
        await click(detailsButton);

        detailsPage = new ADTabDetailsPage();

        await waitUntilAllDisplayed([detailsPage.title, detailsPage.backButton, detailsPage.firstDetail]);

        expect(await detailsPage.title.getText()).not.to.be.empty;
        expect(await detailsPage.firstDetail.getText()).not.to.be.empty;

        await click(detailsPage.backButton);
        await waitUntilCount(listPage.records, beforeRecordsCount + 1);
      });

      it('should load edit ADTab page, fetch data and update', async () => {

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

          await updatePage.targetEndpointInput.clear();
          await updatePage.targetEndpointInput.sendKeys('modified');
          expect(await updatePage.targetEndpointInput.getAttribute('value')).to.match(/modified/);

          await clear(updatePage.levelInput);
          await updatePage.levelInput.sendKeys('6');
          expect(await updatePage.levelInput.getAttribute('value')).to.eq('6');

          const selectedWritable = await updatePage.writableInput.isSelected();
          if (selectedWritable) {
            await updatePage.writableInput.click();
            expect(await updatePage.writableInput.isSelected()).to.be.false;
          } else {
            await updatePage.writableInput.click();
            expect(await updatePage.writableInput.isSelected()).to.be.true;
          }

          await updatePage.displayLogicInput.clear();
          await updatePage.displayLogicInput.sendKeys('modified');
          expect(await updatePage.displayLogicInput.getAttribute('value')).to.match(/modified/);

          await updatePage.readOnlyLogicInput.clear();
          await updatePage.readOnlyLogicInput.sendKeys('modified');
          expect(await updatePage.readOnlyLogicInput.getAttribute('value')).to.match(/modified/);

          await updatePage.filterQueryInput.clear();
          await updatePage.filterQueryInput.sendKeys('modified');
          expect(await updatePage.filterQueryInput.getAttribute('value')).to.match(/modified/);

          await updatePage.orderQueryInput.clear();
          await updatePage.orderQueryInput.sendKeys('modified');
          expect(await updatePage.orderQueryInput.getAttribute('value')).to.match(/modified/);

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

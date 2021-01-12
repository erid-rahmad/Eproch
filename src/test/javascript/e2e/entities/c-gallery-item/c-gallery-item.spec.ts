/* tslint:disable no-unused-expression */
import { browser } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import CGalleryItemComponentsPage, { CGalleryItemDeleteDialog } from './c-gallery-item.page-object';
import CGalleryItemUpdatePage from './c-gallery-item-update.page-object';
import CGalleryItemDetailsPage from './c-gallery-item-details.page-object';

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

describe('CGalleryItem e2e test', () => {
  let navBarPage: NavBarPage;
  let updatePage: CGalleryItemUpdatePage;
  let detailsPage: CGalleryItemDetailsPage;
  let listPage: CGalleryItemComponentsPage;
  /*let deleteDialog: CGalleryItemDeleteDialog;*/
  let beforeRecordsCount = 0;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    await navBarPage.login('admin', 'admin');
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });

  it('should load CGalleryItems', async () => {
    await navBarPage.getEntityPage('c-gallery-item');
    listPage = new CGalleryItemComponentsPage();

    await waitUntilAllDisplayed([listPage.title, listPage.footer]);

    expect(await listPage.title.getText()).not.to.be.empty;
    expect(await listPage.createButton.isEnabled()).to.be.true;

    await waitUntilAnyDisplayed([listPage.noRecords, listPage.table]);
    beforeRecordsCount = (await isVisible(listPage.noRecords)) ? 0 : await getRecordsCount(listPage.table);
  });
  describe('Create flow', () => {
    it('should load create CGalleryItem page', async () => {
      await listPage.createButton.click();
      updatePage = new CGalleryItemUpdatePage();

      await waitUntilAllDisplayed([updatePage.title, updatePage.footer, updatePage.saveButton]);

      expect(await updatePage.title.getAttribute('id')).to.match(/opusWebApp.cGalleryItem.home.createOrEditLabel/);
    });

    /* it('should create and save CGalleryItems', async () => {

      await selectLastOption(updatePage.typeSelect);


      await updatePage.sequenceInput.sendKeys('5');
      expect(await updatePage.sequenceInput.getAttribute('value')).to.eq('5');


      const selectedPreview = await updatePage.previewInput.isSelected();
      if (selectedPreview) {
        await updatePage.previewInput.click();
        expect(await updatePage.previewInput.isSelected()).to.be.false;
      } else {
        await updatePage.previewInput.click();
        expect(await updatePage.previewInput.isSelected()).to.be.true;
      }


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

      // await  selectLastOption(updatePage.cAttachmentSelect);
      // await  selectLastOption(updatePage.adOrganizationSelect);
      // await  selectLastOption(updatePage.cGallerySelect);

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

        deleteDialog = new CGalleryItemDeleteDialog();
        await waitUntilDisplayed(deleteDialog.dialog);

        expect(await deleteDialog.title.getAttribute('id')).to.match(/opusWebApp.cGalleryItem.delete.question/);

        await click(deleteDialog.confirmButton);
        await waitUntilHidden(deleteDialog.dialog);

        expect(await isVisible(deleteDialog.dialog)).to.be.false;
        expect(await listPage.dangerAlert.isDisplayed()).to.be.true;

        await waitUntilCount(listPage.records, beforeRecordsCount);
        expect(await listPage.records.count()).to.eq(beforeRecordsCount);
      });

      it('should load details CGalleryItem page and fetch data', async () => {

        const detailsButton = listPage.getDetailsButton(listPage.records.first());
        await click(detailsButton);

        detailsPage = new CGalleryItemDetailsPage();

        await waitUntilAllDisplayed([detailsPage.title, detailsPage.backButton, detailsPage.firstDetail]);

        expect(await detailsPage.title.getText()).not.to.be.empty;
        expect(await detailsPage.firstDetail.getText()).not.to.be.empty;

        await click(detailsPage.backButton);
        await waitUntilCount(listPage.records, beforeRecordsCount + 1);
      });

      it('should load edit CGalleryItem page, fetch data and update', async () => {

        const editButton = listPage.getEditButton(listPage.records.first());
        await click(editButton);

        await waitUntilAllDisplayed([updatePage.title, updatePage.footer, updatePage.saveButton]);

        expect(await updatePage.title.getText()).not.to.be.empty;

          await clear(updatePage.sequenceInput);
          await updatePage.sequenceInput.sendKeys('6');
          expect(await updatePage.sequenceInput.getAttribute('value')).to.eq('6');

          const selectedPreview = await updatePage.previewInput.isSelected();
          if (selectedPreview) {
            await updatePage.previewInput.click();
            expect(await updatePage.previewInput.isSelected()).to.be.false;
          } else {
            await updatePage.previewInput.click();
            expect(await updatePage.previewInput.isSelected()).to.be.true;
          }

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

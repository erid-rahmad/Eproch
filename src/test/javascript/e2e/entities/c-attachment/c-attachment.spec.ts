/* tslint:disable no-unused-expression */
import { browser } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import CAttachmentComponentsPage, { CAttachmentDeleteDialog } from './c-attachment.page-object';
import CAttachmentUpdatePage from './c-attachment-update.page-object';
import CAttachmentDetailsPage from './c-attachment-details.page-object';

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

describe('CAttachment e2e test', () => {
  let navBarPage: NavBarPage;
  let updatePage: CAttachmentUpdatePage;
  let detailsPage: CAttachmentDetailsPage;
  let listPage: CAttachmentComponentsPage;
  /*let deleteDialog: CAttachmentDeleteDialog;*/
  let beforeRecordsCount = 0;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    await navBarPage.login('admin', 'admin');
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });

  it('should load CAttachments', async () => {
    await navBarPage.getEntityPage('c-attachment');
    listPage = new CAttachmentComponentsPage();

    await waitUntilAllDisplayed([listPage.title, listPage.footer]);

    expect(await listPage.title.getText()).not.to.be.empty;
    expect(await listPage.createButton.isEnabled()).to.be.true;

    await waitUntilAnyDisplayed([listPage.noRecords, listPage.table]);
    beforeRecordsCount = (await isVisible(listPage.noRecords)) ? 0 : await getRecordsCount(listPage.table);
  });
  describe('Create flow', () => {
    it('should load create CAttachment page', async () => {
      await listPage.createButton.click();
      updatePage = new CAttachmentUpdatePage();

      await waitUntilAllDisplayed([updatePage.title, updatePage.footer, updatePage.saveButton]);

      expect(await updatePage.title.getAttribute('id')).to.match(/opusWebApp.cAttachment.home.createOrEditLabel/);
    });

    /* it('should create and save CAttachments', async () => {

      await selectLastOption(updatePage.typeSelect);


      await updatePage.fileNameInput.sendKeys('fileName');
      expect(await updatePage.fileNameInput.getAttribute('value')).to.match(/fileName/);


      await updatePage.imageSmallInput.sendKeys('imageSmall');
      expect(await updatePage.imageSmallInput.getAttribute('value')).to.match(/imageSmall/);


      await updatePage.imageMediumInput.sendKeys('imageMedium');
      expect(await updatePage.imageMediumInput.getAttribute('value')).to.match(/imageMedium/);


      await updatePage.imageLargeInput.sendKeys('imageLarge');
      expect(await updatePage.imageLargeInput.getAttribute('value')).to.match(/imageLarge/);


      await updatePage.mimeTypeInput.sendKeys('mimeType');
      expect(await updatePage.mimeTypeInput.getAttribute('value')).to.match(/mimeType/);


      await updatePage.documentTypeInput.sendKeys('documentType');
      expect(await updatePage.documentTypeInput.getAttribute('value')).to.match(/documentType/);


      await updatePage.uploadDirInput.sendKeys('uploadDir');
      expect(await updatePage.uploadDirInput.getAttribute('value')).to.match(/uploadDir/);


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

      // await  selectLastOption(updatePage.adOrganizationSelect);

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

        deleteDialog = new CAttachmentDeleteDialog();
        await waitUntilDisplayed(deleteDialog.dialog);

        expect(await deleteDialog.title.getAttribute('id')).to.match(/opusWebApp.cAttachment.delete.question/);

        await click(deleteDialog.confirmButton);
        await waitUntilHidden(deleteDialog.dialog);

        expect(await isVisible(deleteDialog.dialog)).to.be.false;
        expect(await listPage.dangerAlert.isDisplayed()).to.be.true;

        await waitUntilCount(listPage.records, beforeRecordsCount);
        expect(await listPage.records.count()).to.eq(beforeRecordsCount);
      });

      it('should load details CAttachment page and fetch data', async () => {

        const detailsButton = listPage.getDetailsButton(listPage.records.first());
        await click(detailsButton);

        detailsPage = new CAttachmentDetailsPage();

        await waitUntilAllDisplayed([detailsPage.title, detailsPage.backButton, detailsPage.firstDetail]);

        expect(await detailsPage.title.getText()).not.to.be.empty;
        expect(await detailsPage.firstDetail.getText()).not.to.be.empty;

        await click(detailsPage.backButton);
        await waitUntilCount(listPage.records, beforeRecordsCount + 1);
      });

      it('should load edit CAttachment page, fetch data and update', async () => {

        const editButton = listPage.getEditButton(listPage.records.first());
        await click(editButton);

        await waitUntilAllDisplayed([updatePage.title, updatePage.footer, updatePage.saveButton]);

        expect(await updatePage.title.getText()).not.to.be.empty;

          await updatePage.fileNameInput.clear();
          await updatePage.fileNameInput.sendKeys('modified');
          expect(await updatePage.fileNameInput.getAttribute('value')).to.match(/modified/);

          await updatePage.imageSmallInput.clear();
          await updatePage.imageSmallInput.sendKeys('modified');
          expect(await updatePage.imageSmallInput.getAttribute('value')).to.match(/modified/);

          await updatePage.imageMediumInput.clear();
          await updatePage.imageMediumInput.sendKeys('modified');
          expect(await updatePage.imageMediumInput.getAttribute('value')).to.match(/modified/);

          await updatePage.imageLargeInput.clear();
          await updatePage.imageLargeInput.sendKeys('modified');
          expect(await updatePage.imageLargeInput.getAttribute('value')).to.match(/modified/);

          await updatePage.mimeTypeInput.clear();
          await updatePage.mimeTypeInput.sendKeys('modified');
          expect(await updatePage.mimeTypeInput.getAttribute('value')).to.match(/modified/);

          await updatePage.documentTypeInput.clear();
          await updatePage.documentTypeInput.sendKeys('modified');
          expect(await updatePage.documentTypeInput.getAttribute('value')).to.match(/modified/);

          await updatePage.uploadDirInput.clear();
          await updatePage.uploadDirInput.sendKeys('modified');
          expect(await updatePage.uploadDirInput.getAttribute('value')).to.match(/modified/);

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

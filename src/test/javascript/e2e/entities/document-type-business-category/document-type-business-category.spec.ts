/* tslint:disable no-unused-expression */
import { browser } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import DocumentTypeBusinessCategoryComponentsPage, {
  DocumentTypeBusinessCategoryDeleteDialog
} from './document-type-business-category.page-object';
import DocumentTypeBusinessCategoryUpdatePage from './document-type-business-category-update.page-object';
import DocumentTypeBusinessCategoryDetailsPage from './document-type-business-category-details.page-object';

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

describe('DocumentTypeBusinessCategory e2e test', () => {
  let navBarPage: NavBarPage;
  let updatePage: DocumentTypeBusinessCategoryUpdatePage;
  let detailsPage: DocumentTypeBusinessCategoryDetailsPage;
  let listPage: DocumentTypeBusinessCategoryComponentsPage;
  let deleteDialog: DocumentTypeBusinessCategoryDeleteDialog;
  let beforeRecordsCount = 0;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    await navBarPage.login('admin', 'admin');
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });

  it('should load DocumentTypeBusinessCategories', async () => {
    await navBarPage.getEntityPage('document-type-business-category');
    listPage = new DocumentTypeBusinessCategoryComponentsPage();

    await waitUntilAllDisplayed([listPage.title, listPage.footer]);

    expect(await listPage.title.getText()).not.to.be.empty;
    expect(await listPage.createButton.isEnabled()).to.be.true;

    await waitUntilAnyDisplayed([listPage.noRecords, listPage.table]);
    beforeRecordsCount = (await isVisible(listPage.noRecords)) ? 0 : await getRecordsCount(listPage.table);
  });
  describe('Create flow', () => {
    it('should load create DocumentTypeBusinessCategory page', async () => {
      await listPage.createButton.click();
      updatePage = new DocumentTypeBusinessCategoryUpdatePage();

      await waitUntilAllDisplayed([updatePage.title, updatePage.footer, updatePage.saveButton]);

      expect(await updatePage.title.getAttribute('id')).to.match(/opusWebApp.documentTypeBusinessCategory.home.createOrEditLabel/);
    });

    it('should create and save DocumentTypeBusinessCategories', async () => {
      const selectedMandatory = await updatePage.mandatoryInput.isSelected();
      if (selectedMandatory) {
        await updatePage.mandatoryInput.click();
        expect(await updatePage.mandatoryInput.isSelected()).to.be.false;
      } else {
        await updatePage.mandatoryInput.click();
        expect(await updatePage.mandatoryInput.isSelected()).to.be.true;
      }

      const selectedAdditional = await updatePage.additionalInput.isSelected();
      if (selectedAdditional) {
        await updatePage.additionalInput.click();
        expect(await updatePage.additionalInput.isSelected()).to.be.false;
      } else {
        await updatePage.additionalInput.click();
        expect(await updatePage.additionalInput.isSelected()).to.be.true;
      }

      // await  selectLastOption(updatePage.documentTypeSelect);
      // await  selectLastOption(updatePage.businessCategorySelect);

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
        const deleteButton = listPage.getDeleteButton(listPage.records.last());
        await click(deleteButton);

        deleteDialog = new DocumentTypeBusinessCategoryDeleteDialog();
        await waitUntilDisplayed(deleteDialog.dialog);

        expect(await deleteDialog.title.getAttribute('id')).to.match(/opusWebApp.documentTypeBusinessCategory.delete.question/);

        await click(deleteDialog.confirmButton);
        await waitUntilHidden(deleteDialog.dialog);

        expect(await isVisible(deleteDialog.dialog)).to.be.false;
        expect(await listPage.dangerAlert.isDisplayed()).to.be.true;

        await waitUntilCount(listPage.records, beforeRecordsCount);
        expect(await listPage.records.count()).to.eq(beforeRecordsCount);
      });

      it('should load details DocumentTypeBusinessCategory page and fetch data', async () => {
        const detailsButton = listPage.getDetailsButton(listPage.records.last());
        await click(detailsButton);

        detailsPage = new DocumentTypeBusinessCategoryDetailsPage();

        await waitUntilAllDisplayed([detailsPage.title, detailsPage.backButton, detailsPage.firstDetail]);

        expect(await detailsPage.title.getText()).not.to.be.empty;
        expect(await detailsPage.firstDetail.getText()).not.to.be.empty;

        await click(detailsPage.backButton);
        await waitUntilCount(listPage.records, beforeRecordsCount + 1);
      });

      it('should load edit DocumentTypeBusinessCategory page, fetch data and update', async () => {
        const editButton = listPage.getEditButton(listPage.records.last());
        await click(editButton);

        await waitUntilAllDisplayed([updatePage.title, updatePage.footer, updatePage.saveButton]);

        expect(await updatePage.title.getText()).not.to.be.empty;

        const selectedMandatory = await updatePage.mandatoryInput.isSelected();
        if (selectedMandatory) {
          await updatePage.mandatoryInput.click();
          expect(await updatePage.mandatoryInput.isSelected()).to.be.false;
        } else {
          await updatePage.mandatoryInput.click();
          expect(await updatePage.mandatoryInput.isSelected()).to.be.true;
        }

        const selectedAdditional = await updatePage.additionalInput.isSelected();
        if (selectedAdditional) {
          await updatePage.additionalInput.click();
          expect(await updatePage.additionalInput.isSelected()).to.be.false;
        } else {
          await updatePage.additionalInput.click();
          expect(await updatePage.additionalInput.isSelected()).to.be.true;
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

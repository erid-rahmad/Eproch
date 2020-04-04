/* tslint:disable no-unused-expression */
import { browser } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import DocumentTypeComponentsPage, { DocumentTypeDeleteDialog } from './document-type.page-object';
import DocumentTypeUpdatePage from './document-type-update.page-object';
import DocumentTypeDetailsPage from './document-type-details.page-object';

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

describe('DocumentType e2e test', () => {
  let navBarPage: NavBarPage;
  let updatePage: DocumentTypeUpdatePage;
  let detailsPage: DocumentTypeDetailsPage;
  let listPage: DocumentTypeComponentsPage;
  let deleteDialog: DocumentTypeDeleteDialog;
  let beforeRecordsCount = 0;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    await navBarPage.login('admin', 'admin');
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });

  it('should load DocumentTypes', async () => {
    await navBarPage.getEntityPage('document-type');
    listPage = new DocumentTypeComponentsPage();

    await waitUntilAllDisplayed([listPage.title, listPage.footer]);

    expect(await listPage.title.getText()).not.to.be.empty;
    expect(await listPage.createButton.isEnabled()).to.be.true;

    await waitUntilAnyDisplayed([listPage.noRecords, listPage.table]);
    beforeRecordsCount = (await isVisible(listPage.noRecords)) ? 0 : await getRecordsCount(listPage.table);
  });
  describe('Create flow', () => {
    it('should load create DocumentType page', async () => {
      await listPage.createButton.click();
      updatePage = new DocumentTypeUpdatePage();

      await waitUntilAllDisplayed([updatePage.title, updatePage.footer, updatePage.saveButton]);

      expect(await updatePage.title.getAttribute('id')).to.match(/opusWebApp.documentType.home.createOrEditLabel/);
    });

    it('should create and save DocumentTypes', async () => {
      await updatePage.nameInput.sendKeys('name');
      expect(await updatePage.nameInput.getAttribute('value')).to.match(/name/);

      await updatePage.descriptionInput.sendKeys('description');
      expect(await updatePage.descriptionInput.getAttribute('value')).to.match(/description/);

      const selectedHasExpirationDate = await updatePage.hasExpirationDateInput.isSelected();
      if (selectedHasExpirationDate) {
        await updatePage.hasExpirationDateInput.click();
        expect(await updatePage.hasExpirationDateInput.isSelected()).to.be.false;
      } else {
        await updatePage.hasExpirationDateInput.click();
        expect(await updatePage.hasExpirationDateInput.isSelected()).to.be.true;
      }

      await updatePage.mandatoryBusinessCategoriesInput.sendKeys('mandatoryBusinessCategories');
      expect(await updatePage.mandatoryBusinessCategoriesInput.getAttribute('value')).to.match(/mandatoryBusinessCategories/);

      await updatePage.additionalBusinessCategoriesInput.sendKeys('additionalBusinessCategories');
      expect(await updatePage.additionalBusinessCategoriesInput.getAttribute('value')).to.match(/additionalBusinessCategories/);

      const selectedMandatoryForCompany = await updatePage.mandatoryForCompanyInput.isSelected();
      if (selectedMandatoryForCompany) {
        await updatePage.mandatoryForCompanyInput.click();
        expect(await updatePage.mandatoryForCompanyInput.isSelected()).to.be.false;
      } else {
        await updatePage.mandatoryForCompanyInput.click();
        expect(await updatePage.mandatoryForCompanyInput.isSelected()).to.be.true;
      }

      const selectedMandatoryForProfessional = await updatePage.mandatoryForProfessionalInput.isSelected();
      if (selectedMandatoryForProfessional) {
        await updatePage.mandatoryForProfessionalInput.click();
        expect(await updatePage.mandatoryForProfessionalInput.isSelected()).to.be.false;
      } else {
        await updatePage.mandatoryForProfessionalInput.click();
        expect(await updatePage.mandatoryForProfessionalInput.isSelected()).to.be.true;
      }

      const selectedAdditionalForCompany = await updatePage.additionalForCompanyInput.isSelected();
      if (selectedAdditionalForCompany) {
        await updatePage.additionalForCompanyInput.click();
        expect(await updatePage.additionalForCompanyInput.isSelected()).to.be.false;
      } else {
        await updatePage.additionalForCompanyInput.click();
        expect(await updatePage.additionalForCompanyInput.isSelected()).to.be.true;
      }

      const selectedAdditionalForProfessional = await updatePage.additionalForProfessionalInput.isSelected();
      if (selectedAdditionalForProfessional) {
        await updatePage.additionalForProfessionalInput.click();
        expect(await updatePage.additionalForProfessionalInput.isSelected()).to.be.false;
      } else {
        await updatePage.additionalForProfessionalInput.click();
        expect(await updatePage.additionalForProfessionalInput.isSelected()).to.be.true;
      }

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

        deleteDialog = new DocumentTypeDeleteDialog();
        await waitUntilDisplayed(deleteDialog.dialog);

        expect(await deleteDialog.title.getAttribute('id')).to.match(/opusWebApp.documentType.delete.question/);

        await click(deleteDialog.confirmButton);
        await waitUntilHidden(deleteDialog.dialog);

        expect(await isVisible(deleteDialog.dialog)).to.be.false;
        expect(await listPage.dangerAlert.isDisplayed()).to.be.true;

        await waitUntilCount(listPage.records, beforeRecordsCount);
        expect(await listPage.records.count()).to.eq(beforeRecordsCount);
      });

      it('should load details DocumentType page and fetch data', async () => {
        const detailsButton = listPage.getDetailsButton(listPage.records.first());
        await click(detailsButton);

        detailsPage = new DocumentTypeDetailsPage();

        await waitUntilAllDisplayed([detailsPage.title, detailsPage.backButton, detailsPage.firstDetail]);

        expect(await detailsPage.title.getText()).not.to.be.empty;
        expect(await detailsPage.firstDetail.getText()).not.to.be.empty;

        await click(detailsPage.backButton);
        await waitUntilCount(listPage.records, beforeRecordsCount + 1);
      });

      it('should load edit DocumentType page, fetch data and update', async () => {
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

        const selectedHasExpirationDate = await updatePage.hasExpirationDateInput.isSelected();
        if (selectedHasExpirationDate) {
          await updatePage.hasExpirationDateInput.click();
          expect(await updatePage.hasExpirationDateInput.isSelected()).to.be.false;
        } else {
          await updatePage.hasExpirationDateInput.click();
          expect(await updatePage.hasExpirationDateInput.isSelected()).to.be.true;
        }

        await updatePage.mandatoryBusinessCategoriesInput.clear();
        await updatePage.mandatoryBusinessCategoriesInput.sendKeys('modified');
        expect(await updatePage.mandatoryBusinessCategoriesInput.getAttribute('value')).to.match(/modified/);

        await updatePage.additionalBusinessCategoriesInput.clear();
        await updatePage.additionalBusinessCategoriesInput.sendKeys('modified');
        expect(await updatePage.additionalBusinessCategoriesInput.getAttribute('value')).to.match(/modified/);

        const selectedMandatoryForCompany = await updatePage.mandatoryForCompanyInput.isSelected();
        if (selectedMandatoryForCompany) {
          await updatePage.mandatoryForCompanyInput.click();
          expect(await updatePage.mandatoryForCompanyInput.isSelected()).to.be.false;
        } else {
          await updatePage.mandatoryForCompanyInput.click();
          expect(await updatePage.mandatoryForCompanyInput.isSelected()).to.be.true;
        }

        const selectedMandatoryForProfessional = await updatePage.mandatoryForProfessionalInput.isSelected();
        if (selectedMandatoryForProfessional) {
          await updatePage.mandatoryForProfessionalInput.click();
          expect(await updatePage.mandatoryForProfessionalInput.isSelected()).to.be.false;
        } else {
          await updatePage.mandatoryForProfessionalInput.click();
          expect(await updatePage.mandatoryForProfessionalInput.isSelected()).to.be.true;
        }

        const selectedAdditionalForCompany = await updatePage.additionalForCompanyInput.isSelected();
        if (selectedAdditionalForCompany) {
          await updatePage.additionalForCompanyInput.click();
          expect(await updatePage.additionalForCompanyInput.isSelected()).to.be.false;
        } else {
          await updatePage.additionalForCompanyInput.click();
          expect(await updatePage.additionalForCompanyInput.isSelected()).to.be.true;
        }

        const selectedAdditionalForProfessional = await updatePage.additionalForProfessionalInput.isSelected();
        if (selectedAdditionalForProfessional) {
          await updatePage.additionalForProfessionalInput.click();
          expect(await updatePage.additionalForProfessionalInput.isSelected()).to.be.false;
        } else {
          await updatePage.additionalForProfessionalInput.click();
          expect(await updatePage.additionalForProfessionalInput.isSelected()).to.be.true;
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
  });
});

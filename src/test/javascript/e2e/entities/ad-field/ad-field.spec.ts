/* tslint:disable no-unused-expression */
import { browser } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import ADFieldComponentsPage, { ADFieldDeleteDialog } from './ad-field.page-object';
import ADFieldUpdatePage from './ad-field-update.page-object';
import ADFieldDetailsPage from './ad-field-details.page-object';

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

describe('ADField e2e test', () => {
  let navBarPage: NavBarPage;
  let updatePage: ADFieldUpdatePage;
  let detailsPage: ADFieldDetailsPage;
  let listPage: ADFieldComponentsPage;
  /*let deleteDialog: ADFieldDeleteDialog;*/
  let beforeRecordsCount = 0;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    await navBarPage.login('admin', 'admin');
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });

  it('should load ADFields', async () => {
    await navBarPage.getEntityPage('ad-field');
    listPage = new ADFieldComponentsPage();

    await waitUntilAllDisplayed([listPage.title, listPage.footer]);

    expect(await listPage.title.getText()).not.to.be.empty;
    expect(await listPage.createButton.isEnabled()).to.be.true;

    await waitUntilAnyDisplayed([listPage.noRecords, listPage.table]);
    beforeRecordsCount = (await isVisible(listPage.noRecords)) ? 0 : await getRecordsCount(listPage.table);
  });
  describe('Create flow', () => {
    it('should load create ADField page', async () => {
      await listPage.createButton.click();
      updatePage = new ADFieldUpdatePage();

      await waitUntilAllDisplayed([updatePage.title, updatePage.footer, updatePage.saveButton]);

      expect(await updatePage.title.getAttribute('id')).to.match(/opusWebApp.aDField.home.createOrEditLabel/);
    });

    /* it('should create and save ADFields', async () => {

      await updatePage.nameInput.sendKeys('name');
      expect(await updatePage.nameInput.getAttribute('value')).to.match(/name/);


      await updatePage.descriptionInput.sendKeys('description');
      expect(await updatePage.descriptionInput.getAttribute('value')).to.match(/description/);


      await updatePage.hintInput.sendKeys('hint');
      expect(await updatePage.hintInput.getAttribute('value')).to.match(/hint/);


      await updatePage.staticTextInput.sendKeys('staticText');
      expect(await updatePage.staticTextInput.getAttribute('value')).to.match(/staticText/);


      const selectedStaticField = await updatePage.staticFieldInput.isSelected();
      if (selectedStaticField) {
        await updatePage.staticFieldInput.click();
        expect(await updatePage.staticFieldInput.isSelected()).to.be.false;
      } else {
        await updatePage.staticFieldInput.click();
        expect(await updatePage.staticFieldInput.isSelected()).to.be.true;
      }


      const selectedLabelOnly = await updatePage.labelOnlyInput.isSelected();
      if (selectedLabelOnly) {
        await updatePage.labelOnlyInput.click();
        expect(await updatePage.labelOnlyInput.isSelected()).to.be.false;
      } else {
        await updatePage.labelOnlyInput.click();
        expect(await updatePage.labelOnlyInput.isSelected()).to.be.true;
      }


      const selectedShowLabel = await updatePage.showLabelInput.isSelected();
      if (selectedShowLabel) {
        await updatePage.showLabelInput.click();
        expect(await updatePage.showLabelInput.isSelected()).to.be.false;
      } else {
        await updatePage.showLabelInput.click();
        expect(await updatePage.showLabelInput.isSelected()).to.be.true;
      }


      const selectedShowInGrid = await updatePage.showInGridInput.isSelected();
      if (selectedShowInGrid) {
        await updatePage.showInGridInput.click();
        expect(await updatePage.showInGridInput.isSelected()).to.be.false;
      } else {
        await updatePage.showInGridInput.click();
        expect(await updatePage.showInGridInput.isSelected()).to.be.true;
      }


      const selectedShowInDetail = await updatePage.showInDetailInput.isSelected();
      if (selectedShowInDetail) {
        await updatePage.showInDetailInput.click();
        expect(await updatePage.showInDetailInput.isSelected()).to.be.false;
      } else {
        await updatePage.showInDetailInput.click();
        expect(await updatePage.showInDetailInput.isSelected()).to.be.true;
      }


      await updatePage.gridSequenceInput.sendKeys('5');
      expect(await updatePage.gridSequenceInput.getAttribute('value')).to.eq('5');


      await updatePage.detailSequenceInput.sendKeys('5');
      expect(await updatePage.detailSequenceInput.getAttribute('value')).to.eq('5');


      await updatePage.displayLogicInput.sendKeys('displayLogic');
      expect(await updatePage.displayLogicInput.getAttribute('value')).to.match(/displayLogic/);


      const selectedWritable = await updatePage.writableInput.isSelected();
      if (selectedWritable) {
        await updatePage.writableInput.click();
        expect(await updatePage.writableInput.isSelected()).to.be.false;
      } else {
        await updatePage.writableInput.click();
        expect(await updatePage.writableInput.isSelected()).to.be.true;
      }


      await updatePage.columnNoInput.sendKeys('5');
      expect(await updatePage.columnNoInput.getAttribute('value')).to.eq('5');


      await updatePage.columnSpanInput.sendKeys('5');
      expect(await updatePage.columnSpanInput.getAttribute('value')).to.eq('5');


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
      // await  selectLastOption(updatePage.adReferenceSelect);
      // await  selectLastOption(updatePage.adColumnSelect);
      // await  selectLastOption(updatePage.adTabSelect);

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

        deleteDialog = new ADFieldDeleteDialog();
        await waitUntilDisplayed(deleteDialog.dialog);

        expect(await deleteDialog.title.getAttribute('id')).to.match(/opusWebApp.aDField.delete.question/);

        await click(deleteDialog.confirmButton);
        await waitUntilHidden(deleteDialog.dialog);

        expect(await isVisible(deleteDialog.dialog)).to.be.false;
        expect(await listPage.dangerAlert.isDisplayed()).to.be.true;

        await waitUntilCount(listPage.records, beforeRecordsCount);
        expect(await listPage.records.count()).to.eq(beforeRecordsCount);
      });

      it('should load details ADField page and fetch data', async () => {

        const detailsButton = listPage.getDetailsButton(listPage.records.first());
        await click(detailsButton);

        detailsPage = new ADFieldDetailsPage();

        await waitUntilAllDisplayed([detailsPage.title, detailsPage.backButton, detailsPage.firstDetail]);

        expect(await detailsPage.title.getText()).not.to.be.empty;
        expect(await detailsPage.firstDetail.getText()).not.to.be.empty;

        await click(detailsPage.backButton);
        await waitUntilCount(listPage.records, beforeRecordsCount + 1);
      });

      it('should load edit ADField page, fetch data and update', async () => {

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

          await updatePage.hintInput.clear();
          await updatePage.hintInput.sendKeys('modified');
          expect(await updatePage.hintInput.getAttribute('value')).to.match(/modified/);

          await updatePage.staticTextInput.clear();
          await updatePage.staticTextInput.sendKeys('modified');
          expect(await updatePage.staticTextInput.getAttribute('value')).to.match(/modified/);

          const selectedStaticField = await updatePage.staticFieldInput.isSelected();
          if (selectedStaticField) {
            await updatePage.staticFieldInput.click();
            expect(await updatePage.staticFieldInput.isSelected()).to.be.false;
          } else {
            await updatePage.staticFieldInput.click();
            expect(await updatePage.staticFieldInput.isSelected()).to.be.true;
          }

          const selectedLabelOnly = await updatePage.labelOnlyInput.isSelected();
          if (selectedLabelOnly) {
            await updatePage.labelOnlyInput.click();
            expect(await updatePage.labelOnlyInput.isSelected()).to.be.false;
          } else {
            await updatePage.labelOnlyInput.click();
            expect(await updatePage.labelOnlyInput.isSelected()).to.be.true;
          }

          const selectedShowLabel = await updatePage.showLabelInput.isSelected();
          if (selectedShowLabel) {
            await updatePage.showLabelInput.click();
            expect(await updatePage.showLabelInput.isSelected()).to.be.false;
          } else {
            await updatePage.showLabelInput.click();
            expect(await updatePage.showLabelInput.isSelected()).to.be.true;
          }

          const selectedShowInGrid = await updatePage.showInGridInput.isSelected();
          if (selectedShowInGrid) {
            await updatePage.showInGridInput.click();
            expect(await updatePage.showInGridInput.isSelected()).to.be.false;
          } else {
            await updatePage.showInGridInput.click();
            expect(await updatePage.showInGridInput.isSelected()).to.be.true;
          }

          const selectedShowInDetail = await updatePage.showInDetailInput.isSelected();
          if (selectedShowInDetail) {
            await updatePage.showInDetailInput.click();
            expect(await updatePage.showInDetailInput.isSelected()).to.be.false;
          } else {
            await updatePage.showInDetailInput.click();
            expect(await updatePage.showInDetailInput.isSelected()).to.be.true;
          }

          await clear(updatePage.gridSequenceInput);
          await updatePage.gridSequenceInput.sendKeys('6');
          expect(await updatePage.gridSequenceInput.getAttribute('value')).to.eq('6');

          await clear(updatePage.detailSequenceInput);
          await updatePage.detailSequenceInput.sendKeys('6');
          expect(await updatePage.detailSequenceInput.getAttribute('value')).to.eq('6');

          await updatePage.displayLogicInput.clear();
          await updatePage.displayLogicInput.sendKeys('modified');
          expect(await updatePage.displayLogicInput.getAttribute('value')).to.match(/modified/);

          const selectedWritable = await updatePage.writableInput.isSelected();
          if (selectedWritable) {
            await updatePage.writableInput.click();
            expect(await updatePage.writableInput.isSelected()).to.be.false;
          } else {
            await updatePage.writableInput.click();
            expect(await updatePage.writableInput.isSelected()).to.be.true;
          }

          await clear(updatePage.columnNoInput);
          await updatePage.columnNoInput.sendKeys('6');
          expect(await updatePage.columnNoInput.getAttribute('value')).to.eq('6');

          await clear(updatePage.columnSpanInput);
          await updatePage.columnSpanInput.sendKeys('6');
          expect(await updatePage.columnSpanInput.getAttribute('value')).to.eq('6');

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

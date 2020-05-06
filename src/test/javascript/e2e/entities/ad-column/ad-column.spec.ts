/* tslint:disable no-unused-expression */
import { browser } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import ADColumnComponentsPage, { ADColumnDeleteDialog } from './ad-column.page-object';
import ADColumnUpdatePage from './ad-column-update.page-object';
import ADColumnDetailsPage from './ad-column-details.page-object';

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

describe('ADColumn e2e test', () => {
  let navBarPage: NavBarPage;
  let updatePage: ADColumnUpdatePage;
  let detailsPage: ADColumnDetailsPage;
  let listPage: ADColumnComponentsPage;
  /*let deleteDialog: ADColumnDeleteDialog;*/
  let beforeRecordsCount = 0;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    await navBarPage.login('admin', 'admin');
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });

  it('should load ADColumns', async () => {
    await navBarPage.getEntityPage('ad-column');
    listPage = new ADColumnComponentsPage();

    await waitUntilAllDisplayed([listPage.title, listPage.footer]);

    expect(await listPage.title.getText()).not.to.be.empty;
    expect(await listPage.createButton.isEnabled()).to.be.true;

    await waitUntilAnyDisplayed([listPage.noRecords, listPage.table]);
    beforeRecordsCount = (await isVisible(listPage.noRecords)) ? 0 : await getRecordsCount(listPage.table);
  });
  describe('Create flow', () => {
    it('should load create ADColumn page', async () => {
      await listPage.createButton.click();
      updatePage = new ADColumnUpdatePage();

      await waitUntilAllDisplayed([updatePage.title, updatePage.footer, updatePage.saveButton]);

      expect(await updatePage.title.getAttribute('id')).to.match(/opusWebApp.aDColumn.home.createOrEditLabel/);
    });

    /* it('should create and save ADColumns', async () => {

      await updatePage.nameInput.sendKeys('name');
      expect(await updatePage.nameInput.getAttribute('value')).to.match(/name/);


      await updatePage.sqlNameInput.sendKeys('sqlName');
      expect(await updatePage.sqlNameInput.getAttribute('value')).to.match(/sqlName/);


      await updatePage.descriptionInput.sendKeys('description');
      expect(await updatePage.descriptionInput.getAttribute('value')).to.match(/description/);


      await updatePage.fieldLengthInput.sendKeys('5');
      expect(await updatePage.fieldLengthInput.getAttribute('value')).to.eq('5');


      const selectedKey = await updatePage.keyInput.isSelected();
      if (selectedKey) {
        await updatePage.keyInput.click();
        expect(await updatePage.keyInput.isSelected()).to.be.false;
      } else {
        await updatePage.keyInput.click();
        expect(await updatePage.keyInput.isSelected()).to.be.true;
      }


      await selectLastOption(updatePage.typeSelect);


      const selectedMandatory = await updatePage.mandatoryInput.isSelected();
      if (selectedMandatory) {
        await updatePage.mandatoryInput.click();
        expect(await updatePage.mandatoryInput.isSelected()).to.be.false;
      } else {
        await updatePage.mandatoryInput.click();
        expect(await updatePage.mandatoryInput.isSelected()).to.be.true;
      }


      await updatePage.mandatoryLogicInput.sendKeys('mandatoryLogic');
      expect(await updatePage.mandatoryLogicInput.getAttribute('value')).to.match(/mandatoryLogic/);


      await updatePage.readOnlyLogicInput.sendKeys('readOnlyLogic');
      expect(await updatePage.readOnlyLogicInput.getAttribute('value')).to.match(/readOnlyLogic/);


      const selectedUpdatable = await updatePage.updatableInput.isSelected();
      if (selectedUpdatable) {
        await updatePage.updatableInput.click();
        expect(await updatePage.updatableInput.isSelected()).to.be.false;
      } else {
        await updatePage.updatableInput.click();
        expect(await updatePage.updatableInput.isSelected()).to.be.true;
      }


      await updatePage.defaultValueInput.sendKeys('defaultValue');
      expect(await updatePage.defaultValueInput.getAttribute('value')).to.match(/defaultValue/);


      await updatePage.formatPatternInput.sendKeys('formatPattern');
      expect(await updatePage.formatPatternInput.getAttribute('value')).to.match(/formatPattern/);


      await updatePage.minLengthInput.sendKeys('5');
      expect(await updatePage.minLengthInput.getAttribute('value')).to.eq('5');


      await updatePage.maxLengthInput.sendKeys('5');
      expect(await updatePage.maxLengthInput.getAttribute('value')).to.eq('5');


      await updatePage.minValueInput.sendKeys('5');
      expect(await updatePage.minValueInput.getAttribute('value')).to.eq('5');


      await updatePage.maxValueInput.sendKeys('5');
      expect(await updatePage.maxValueInput.getAttribute('value')).to.eq('5');


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
      // await  selectLastOption(updatePage.adTableSelect);

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

        deleteDialog = new ADColumnDeleteDialog();
        await waitUntilDisplayed(deleteDialog.dialog);

        expect(await deleteDialog.title.getAttribute('id')).to.match(/opusWebApp.aDColumn.delete.question/);

        await click(deleteDialog.confirmButton);
        await waitUntilHidden(deleteDialog.dialog);

        expect(await isVisible(deleteDialog.dialog)).to.be.false;
        expect(await listPage.dangerAlert.isDisplayed()).to.be.true;

        await waitUntilCount(listPage.records, beforeRecordsCount);
        expect(await listPage.records.count()).to.eq(beforeRecordsCount);
      });

      it('should load details ADColumn page and fetch data', async () => {

        const detailsButton = listPage.getDetailsButton(listPage.records.first());
        await click(detailsButton);

        detailsPage = new ADColumnDetailsPage();

        await waitUntilAllDisplayed([detailsPage.title, detailsPage.backButton, detailsPage.firstDetail]);

        expect(await detailsPage.title.getText()).not.to.be.empty;
        expect(await detailsPage.firstDetail.getText()).not.to.be.empty;

        await click(detailsPage.backButton);
        await waitUntilCount(listPage.records, beforeRecordsCount + 1);
      });

      it('should load edit ADColumn page, fetch data and update', async () => {

        const editButton = listPage.getEditButton(listPage.records.first());
        await click(editButton);

        await waitUntilAllDisplayed([updatePage.title, updatePage.footer, updatePage.saveButton]);

        expect(await updatePage.title.getText()).not.to.be.empty;

          await updatePage.nameInput.clear();
          await updatePage.nameInput.sendKeys('modified');
          expect(await updatePage.nameInput.getAttribute('value')).to.match(/modified/);

          await updatePage.sqlNameInput.clear();
          await updatePage.sqlNameInput.sendKeys('modified');
          expect(await updatePage.sqlNameInput.getAttribute('value')).to.match(/modified/);

          await updatePage.descriptionInput.clear();
          await updatePage.descriptionInput.sendKeys('modified');
          expect(await updatePage.descriptionInput.getAttribute('value')).to.match(/modified/);

          await clear(updatePage.fieldLengthInput);
          await updatePage.fieldLengthInput.sendKeys('6');
          expect(await updatePage.fieldLengthInput.getAttribute('value')).to.eq('6');

          const selectedKey = await updatePage.keyInput.isSelected();
          if (selectedKey) {
            await updatePage.keyInput.click();
            expect(await updatePage.keyInput.isSelected()).to.be.false;
          } else {
            await updatePage.keyInput.click();
            expect(await updatePage.keyInput.isSelected()).to.be.true;
          }

          const selectedMandatory = await updatePage.mandatoryInput.isSelected();
          if (selectedMandatory) {
            await updatePage.mandatoryInput.click();
            expect(await updatePage.mandatoryInput.isSelected()).to.be.false;
          } else {
            await updatePage.mandatoryInput.click();
            expect(await updatePage.mandatoryInput.isSelected()).to.be.true;
          }

          await updatePage.mandatoryLogicInput.clear();
          await updatePage.mandatoryLogicInput.sendKeys('modified');
          expect(await updatePage.mandatoryLogicInput.getAttribute('value')).to.match(/modified/);

          await updatePage.readOnlyLogicInput.clear();
          await updatePage.readOnlyLogicInput.sendKeys('modified');
          expect(await updatePage.readOnlyLogicInput.getAttribute('value')).to.match(/modified/);

          const selectedUpdatable = await updatePage.updatableInput.isSelected();
          if (selectedUpdatable) {
            await updatePage.updatableInput.click();
            expect(await updatePage.updatableInput.isSelected()).to.be.false;
          } else {
            await updatePage.updatableInput.click();
            expect(await updatePage.updatableInput.isSelected()).to.be.true;
          }

          await updatePage.defaultValueInput.clear();
          await updatePage.defaultValueInput.sendKeys('modified');
          expect(await updatePage.defaultValueInput.getAttribute('value')).to.match(/modified/);

          await updatePage.formatPatternInput.clear();
          await updatePage.formatPatternInput.sendKeys('modified');
          expect(await updatePage.formatPatternInput.getAttribute('value')).to.match(/modified/);

          await clear(updatePage.minLengthInput);
          await updatePage.minLengthInput.sendKeys('6');
          expect(await updatePage.minLengthInput.getAttribute('value')).to.eq('6');

          await clear(updatePage.maxLengthInput);
          await updatePage.maxLengthInput.sendKeys('6');
          expect(await updatePage.maxLengthInput.getAttribute('value')).to.eq('6');

          await clear(updatePage.minValueInput);
          await updatePage.minValueInput.sendKeys('6');
          expect(await updatePage.minValueInput.getAttribute('value')).to.eq('6');

          await clear(updatePage.maxValueInput);
          await updatePage.maxValueInput.sendKeys('6');
          expect(await updatePage.maxValueInput.getAttribute('value')).to.eq('6');

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

/* tslint:disable no-unused-expression */
import { browser } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import ADFieldGroupComponentsPage, { ADFieldGroupDeleteDialog } from './ad-field-group.page-object';
import ADFieldGroupUpdatePage from './ad-field-group-update.page-object';
import ADFieldGroupDetailsPage from './ad-field-group-details.page-object';

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

describe('ADFieldGroup e2e test', () => {
  let navBarPage: NavBarPage;
  let updatePage: ADFieldGroupUpdatePage;
  let detailsPage: ADFieldGroupDetailsPage;
  let listPage: ADFieldGroupComponentsPage;
  let deleteDialog: ADFieldGroupDeleteDialog;
  let beforeRecordsCount = 0;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    await navBarPage.login('admin', 'admin');
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });

  it('should load ADFieldGroups', async () => {
    await navBarPage.getEntityPage('ad-field-group');
    listPage = new ADFieldGroupComponentsPage();

    await waitUntilAllDisplayed([listPage.title, listPage.footer]);

    expect(await listPage.title.getText()).not.to.be.empty;
    expect(await listPage.createButton.isEnabled()).to.be.true;

    await waitUntilAnyDisplayed([listPage.noRecords, listPage.table]);
    beforeRecordsCount = (await isVisible(listPage.noRecords)) ? 0 : await getRecordsCount(listPage.table);
  });
  describe('Create flow', () => {
    it('should load create ADFieldGroup page', async () => {
      await listPage.createButton.click();
      updatePage = new ADFieldGroupUpdatePage();

      await waitUntilAllDisplayed([updatePage.title, updatePage.footer, updatePage.saveButton]);

      expect(await updatePage.title.getAttribute('id')).to.match(/opusWebApp.aDFieldGroup.home.createOrEditLabel/);
    });

    it('should create and save ADFieldGroups', async () => {
      await updatePage.nameInput.sendKeys('name');
      expect(await updatePage.nameInput.getAttribute('value')).to.match(/name/);

      const selectedCollapsible = await updatePage.collapsibleInput.isSelected();
      if (selectedCollapsible) {
        await updatePage.collapsibleInput.click();
        expect(await updatePage.collapsibleInput.isSelected()).to.be.false;
      } else {
        await updatePage.collapsibleInput.click();
        expect(await updatePage.collapsibleInput.isSelected()).to.be.true;
      }

      const selectedCollapseByDefault = await updatePage.collapseByDefaultInput.isSelected();
      if (selectedCollapseByDefault) {
        await updatePage.collapseByDefaultInput.click();
        expect(await updatePage.collapseByDefaultInput.isSelected()).to.be.false;
      } else {
        await updatePage.collapseByDefaultInput.click();
        expect(await updatePage.collapseByDefaultInput.isSelected()).to.be.true;
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

        deleteDialog = new ADFieldGroupDeleteDialog();
        await waitUntilDisplayed(deleteDialog.dialog);

        expect(await deleteDialog.title.getAttribute('id')).to.match(/opusWebApp.aDFieldGroup.delete.question/);

        await click(deleteDialog.confirmButton);
        await waitUntilHidden(deleteDialog.dialog);

        expect(await isVisible(deleteDialog.dialog)).to.be.false;
        expect(await listPage.dangerAlert.isDisplayed()).to.be.true;

        await waitUntilCount(listPage.records, beforeRecordsCount);
        expect(await listPage.records.count()).to.eq(beforeRecordsCount);
      });

      it('should load details ADFieldGroup page and fetch data', async () => {
        const detailsButton = listPage.getDetailsButton(listPage.records.first());
        await click(detailsButton);

        detailsPage = new ADFieldGroupDetailsPage();

        await waitUntilAllDisplayed([detailsPage.title, detailsPage.backButton, detailsPage.firstDetail]);

        expect(await detailsPage.title.getText()).not.to.be.empty;
        expect(await detailsPage.firstDetail.getText()).not.to.be.empty;

        await click(detailsPage.backButton);
        await waitUntilCount(listPage.records, beforeRecordsCount + 1);
      });

      it('should load edit ADFieldGroup page, fetch data and update', async () => {
        const editButton = listPage.getEditButton(listPage.records.first());
        await click(editButton);

        await waitUntilAllDisplayed([updatePage.title, updatePage.footer, updatePage.saveButton]);

        expect(await updatePage.title.getText()).not.to.be.empty;

        await updatePage.nameInput.clear();
        await updatePage.nameInput.sendKeys('modified');
        expect(await updatePage.nameInput.getAttribute('value')).to.match(/modified/);

        const selectedCollapsible = await updatePage.collapsibleInput.isSelected();
        if (selectedCollapsible) {
          await updatePage.collapsibleInput.click();
          expect(await updatePage.collapsibleInput.isSelected()).to.be.false;
        } else {
          await updatePage.collapsibleInput.click();
          expect(await updatePage.collapsibleInput.isSelected()).to.be.true;
        }

        const selectedCollapseByDefault = await updatePage.collapseByDefaultInput.isSelected();
        if (selectedCollapseByDefault) {
          await updatePage.collapseByDefaultInput.click();
          expect(await updatePage.collapseByDefaultInput.isSelected()).to.be.false;
        } else {
          await updatePage.collapseByDefaultInput.click();
          expect(await updatePage.collapseByDefaultInput.isSelected()).to.be.true;
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

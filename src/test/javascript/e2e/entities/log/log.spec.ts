import { browser, ExpectedConditions as ec /* , protractor, promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  LogComponentsPage,
  /* LogDeleteDialog, */
  LogUpdatePage,
} from './log.page-object';

const expect = chai.expect;

describe('Log e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let logComponentsPage: LogComponentsPage;
  let logUpdatePage: LogUpdatePage;
  /* let logDeleteDialog: LogDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Logs', async () => {
    await navBarPage.goToEntity('log');
    logComponentsPage = new LogComponentsPage();
    await browser.wait(ec.visibilityOf(logComponentsPage.title), 5000);
    expect(await logComponentsPage.getTitle()).to.eq('shoppedApp.log.home.title');
    await browser.wait(ec.or(ec.visibilityOf(logComponentsPage.entities), ec.visibilityOf(logComponentsPage.noResult)), 1000);
  });

  it('should load create Log page', async () => {
    await logComponentsPage.clickOnCreateButton();
    logUpdatePage = new LogUpdatePage();
    expect(await logUpdatePage.getPageTitle()).to.eq('shoppedApp.log.home.createOrEditLabel');
    await logUpdatePage.cancel();
  });

  /* it('should create and save Logs', async () => {
        const nbButtonsBeforeCreate = await logComponentsPage.countDeleteButtons();

        await logComponentsPage.clickOnCreateButton();

        await promise.all([
            logUpdatePage.setDescriptionInput('description'),
            logUpdatePage.setCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            logUpdatePage.typeSelectLastOption(),
            logUpdatePage.userSelectLastOption(),
        ]);

        expect(await logUpdatePage.getDescriptionInput()).to.eq('description', 'Expected Description value to be equals to description');
        expect(await logUpdatePage.getCreatedInput()).to.contain('2001-01-01T02:30', 'Expected created value to be equals to 2000-12-31');

        await logUpdatePage.save();
        expect(await logUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await logComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /* it('should delete last Log', async () => {
        const nbButtonsBeforeDelete = await logComponentsPage.countDeleteButtons();
        await logComponentsPage.clickOnLastDeleteButton();

        logDeleteDialog = new LogDeleteDialog();
        expect(await logDeleteDialog.getDialogTitle())
            .to.eq('shoppedApp.log.delete.question');
        await logDeleteDialog.clickOnConfirmButton();

        expect(await logComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});

import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { LogTypeComponentsPage, LogTypeDeleteDialog, LogTypeUpdatePage } from './log-type.page-object';

const expect = chai.expect;

describe('LogType e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let logTypeComponentsPage: LogTypeComponentsPage;
  let logTypeUpdatePage: LogTypeUpdatePage;
  let logTypeDeleteDialog: LogTypeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load LogTypes', async () => {
    await navBarPage.goToEntity('log-type');
    logTypeComponentsPage = new LogTypeComponentsPage();
    await browser.wait(ec.visibilityOf(logTypeComponentsPage.title), 5000);
    expect(await logTypeComponentsPage.getTitle()).to.eq('shoppedApp.logType.home.title');
    await browser.wait(ec.or(ec.visibilityOf(logTypeComponentsPage.entities), ec.visibilityOf(logTypeComponentsPage.noResult)), 1000);
  });

  it('should load create LogType page', async () => {
    await logTypeComponentsPage.clickOnCreateButton();
    logTypeUpdatePage = new LogTypeUpdatePage();
    expect(await logTypeUpdatePage.getPageTitle()).to.eq('shoppedApp.logType.home.createOrEditLabel');
    await logTypeUpdatePage.cancel();
  });

  it('should create and save LogTypes', async () => {
    const nbButtonsBeforeCreate = await logTypeComponentsPage.countDeleteButtons();

    await logTypeComponentsPage.clickOnCreateButton();

    await promise.all([
      logTypeUpdatePage.setNameInput('name'),
      logTypeUpdatePage.setTemplateInput('template'),
      logTypeUpdatePage.statusSelectLastOption(),
    ]);

    expect(await logTypeUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await logTypeUpdatePage.getTemplateInput()).to.eq('template', 'Expected Template value to be equals to template');

    await logTypeUpdatePage.save();
    expect(await logTypeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await logTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last LogType', async () => {
    const nbButtonsBeforeDelete = await logTypeComponentsPage.countDeleteButtons();
    await logTypeComponentsPage.clickOnLastDeleteButton();

    logTypeDeleteDialog = new LogTypeDeleteDialog();
    expect(await logTypeDeleteDialog.getDialogTitle()).to.eq('shoppedApp.logType.delete.question');
    await logTypeDeleteDialog.clickOnConfirmButton();

    expect(await logTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});

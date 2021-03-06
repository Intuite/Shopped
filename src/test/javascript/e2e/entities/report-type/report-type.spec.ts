import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ReportTypeComponentsPage, ReportTypeDeleteDialog, ReportTypeUpdatePage } from './report-type.page-object';

const expect = chai.expect;

describe('ReportType e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let reportTypeComponentsPage: ReportTypeComponentsPage;
  let reportTypeUpdatePage: ReportTypeUpdatePage;
  let reportTypeDeleteDialog: ReportTypeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ReportTypes', async () => {
    await navBarPage.goToEntity('report-type');
    reportTypeComponentsPage = new ReportTypeComponentsPage();
    await browser.wait(ec.visibilityOf(reportTypeComponentsPage.title), 5000);
    expect(await reportTypeComponentsPage.getTitle()).to.eq('shoppedApp.reportType.home.title');
    await browser.wait(ec.or(ec.visibilityOf(reportTypeComponentsPage.entities), ec.visibilityOf(reportTypeComponentsPage.noResult)), 1000);
  });

  it('should load create ReportType page', async () => {
    await reportTypeComponentsPage.clickOnCreateButton();
    reportTypeUpdatePage = new ReportTypeUpdatePage();
    expect(await reportTypeUpdatePage.getPageTitle()).to.eq('shoppedApp.reportType.home.createOrEditLabel');
    await reportTypeUpdatePage.cancel();
  });

  it('should create and save ReportTypes', async () => {
    const nbButtonsBeforeCreate = await reportTypeComponentsPage.countDeleteButtons();

    await reportTypeComponentsPage.clickOnCreateButton();

    await promise.all([
      reportTypeUpdatePage.setNameInput('name'),
      reportTypeUpdatePage.setTextInput('text'),
      reportTypeUpdatePage.statusSelectLastOption(),
    ]);

    expect(await reportTypeUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await reportTypeUpdatePage.getTextInput()).to.eq('text', 'Expected Text value to be equals to text');

    await reportTypeUpdatePage.save();
    expect(await reportTypeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await reportTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last ReportType', async () => {
    const nbButtonsBeforeDelete = await reportTypeComponentsPage.countDeleteButtons();
    await reportTypeComponentsPage.clickOnLastDeleteButton();

    reportTypeDeleteDialog = new ReportTypeDeleteDialog();
    expect(await reportTypeDeleteDialog.getDialogTitle()).to.eq('shoppedApp.reportType.delete.question');
    await reportTypeDeleteDialog.clickOnConfirmButton();

    expect(await reportTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});

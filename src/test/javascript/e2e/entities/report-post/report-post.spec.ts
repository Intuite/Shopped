import { browser, ExpectedConditions as ec /* , protractor, promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  ReportPostComponentsPage,
  /* ReportPostDeleteDialog, */
  ReportPostUpdatePage,
} from './report-post.page-object';

const expect = chai.expect;

describe('ReportPost e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let reportPostComponentsPage: ReportPostComponentsPage;
  let reportPostUpdatePage: ReportPostUpdatePage;
  /* let reportPostDeleteDialog: ReportPostDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ReportPosts', async () => {
    await navBarPage.goToEntity('report-post');
    reportPostComponentsPage = new ReportPostComponentsPage();
    await browser.wait(ec.visibilityOf(reportPostComponentsPage.title), 5000);
    expect(await reportPostComponentsPage.getTitle()).to.eq('shoppedApp.reportPost.home.title');
    await browser.wait(ec.or(ec.visibilityOf(reportPostComponentsPage.entities), ec.visibilityOf(reportPostComponentsPage.noResult)), 1000);
  });

  it('should load create ReportPost page', async () => {
    await reportPostComponentsPage.clickOnCreateButton();
    reportPostUpdatePage = new ReportPostUpdatePage();
    expect(await reportPostUpdatePage.getPageTitle()).to.eq('shoppedApp.reportPost.home.createOrEditLabel');
    await reportPostUpdatePage.cancel();
  });

  /* it('should create and save ReportPosts', async () => {
        const nbButtonsBeforeCreate = await reportPostComponentsPage.countDeleteButtons();

        await reportPostComponentsPage.clickOnCreateButton();

        await promise.all([
            reportPostUpdatePage.setCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            reportPostUpdatePage.statusSelectLastOption(),
            reportPostUpdatePage.typeSelectLastOption(),
            reportPostUpdatePage.postSelectLastOption(),
            reportPostUpdatePage.userSelectLastOption(),
        ]);

        expect(await reportPostUpdatePage.getCreatedInput()).to.contain('2001-01-01T02:30', 'Expected created value to be equals to 2000-12-31');

        await reportPostUpdatePage.save();
        expect(await reportPostUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await reportPostComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /* it('should delete last ReportPost', async () => {
        const nbButtonsBeforeDelete = await reportPostComponentsPage.countDeleteButtons();
        await reportPostComponentsPage.clickOnLastDeleteButton();

        reportPostDeleteDialog = new ReportPostDeleteDialog();
        expect(await reportPostDeleteDialog.getDialogTitle())
            .to.eq('shoppedApp.reportPost.delete.question');
        await reportPostDeleteDialog.clickOnConfirmButton();

        expect(await reportPostComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});

import { browser, ExpectedConditions as ec /* , protractor, promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  ReportCommentComponentsPage,
  /* ReportCommentDeleteDialog, */
  ReportCommentUpdatePage,
} from './report-comment.page-object';

const expect = chai.expect;

describe('ReportComment e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let reportCommentComponentsPage: ReportCommentComponentsPage;
  let reportCommentUpdatePage: ReportCommentUpdatePage;
  /* let reportCommentDeleteDialog: ReportCommentDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ReportComments', async () => {
    await navBarPage.goToEntity('report-comment');
    reportCommentComponentsPage = new ReportCommentComponentsPage();
    await browser.wait(ec.visibilityOf(reportCommentComponentsPage.title), 5000);
    expect(await reportCommentComponentsPage.getTitle()).to.eq('shoppedApp.reportComment.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(reportCommentComponentsPage.entities), ec.visibilityOf(reportCommentComponentsPage.noResult)),
      1000
    );
  });

  it('should load create ReportComment page', async () => {
    await reportCommentComponentsPage.clickOnCreateButton();
    reportCommentUpdatePage = new ReportCommentUpdatePage();
    expect(await reportCommentUpdatePage.getPageTitle()).to.eq('shoppedApp.reportComment.home.createOrEditLabel');
    await reportCommentUpdatePage.cancel();
  });

  /* it('should create and save ReportComments', async () => {
        const nbButtonsBeforeCreate = await reportCommentComponentsPage.countDeleteButtons();

        await reportCommentComponentsPage.clickOnCreateButton();

        await promise.all([
            reportCommentUpdatePage.setCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            reportCommentUpdatePage.statusSelectLastOption(),
            reportCommentUpdatePage.typeSelectLastOption(),
            reportCommentUpdatePage.commentSelectLastOption(),
            reportCommentUpdatePage.userSelectLastOption(),
        ]);

        expect(await reportCommentUpdatePage.getCreatedInput()).to.contain('2001-01-01T02:30', 'Expected created value to be equals to 2000-12-31');

        await reportCommentUpdatePage.save();
        expect(await reportCommentUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await reportCommentComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /* it('should delete last ReportComment', async () => {
        const nbButtonsBeforeDelete = await reportCommentComponentsPage.countDeleteButtons();
        await reportCommentComponentsPage.clickOnLastDeleteButton();

        reportCommentDeleteDialog = new ReportCommentDeleteDialog();
        expect(await reportCommentDeleteDialog.getDialogTitle())
            .to.eq('shoppedApp.reportComment.delete.question');
        await reportCommentDeleteDialog.clickOnConfirmButton();

        expect(await reportCommentComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});

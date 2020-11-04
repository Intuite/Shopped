import { browser, ExpectedConditions as ec /* , protractor, promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  CommendationComponentsPage,
  /* CommendationDeleteDialog, */
  CommendationUpdatePage,
} from './commendation.page-object';

const expect = chai.expect;

describe('Commendation e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let commendationComponentsPage: CommendationComponentsPage;
  let commendationUpdatePage: CommendationUpdatePage;
  /* let commendationDeleteDialog: CommendationDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Commendations', async () => {
    await navBarPage.goToEntity('commendation');
    commendationComponentsPage = new CommendationComponentsPage();
    await browser.wait(ec.visibilityOf(commendationComponentsPage.title), 5000);
    expect(await commendationComponentsPage.getTitle()).to.eq('shoppedApp.commendation.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(commendationComponentsPage.entities), ec.visibilityOf(commendationComponentsPage.noResult)),
      1000
    );
  });

  it('should load create Commendation page', async () => {
    await commendationComponentsPage.clickOnCreateButton();
    commendationUpdatePage = new CommendationUpdatePage();
    expect(await commendationUpdatePage.getPageTitle()).to.eq('shoppedApp.commendation.home.createOrEditLabel');
    await commendationUpdatePage.cancel();
  });

  /* it('should create and save Commendations', async () => {
        const nbButtonsBeforeCreate = await commendationComponentsPage.countDeleteButtons();

        await commendationComponentsPage.clickOnCreateButton();

        await promise.all([
            commendationUpdatePage.setDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            commendationUpdatePage.postSelectLastOption(),
            commendationUpdatePage.awardSelectLastOption(),
            commendationUpdatePage.userSelectLastOption(),
        ]);

        expect(await commendationUpdatePage.getDateInput()).to.contain('2001-01-01T02:30', 'Expected date value to be equals to 2000-12-31');

        await commendationUpdatePage.save();
        expect(await commendationUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await commendationComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /* it('should delete last Commendation', async () => {
        const nbButtonsBeforeDelete = await commendationComponentsPage.countDeleteButtons();
        await commendationComponentsPage.clickOnLastDeleteButton();

        commendationDeleteDialog = new CommendationDeleteDialog();
        expect(await commendationDeleteDialog.getDialogTitle())
            .to.eq('shoppedApp.commendation.delete.question');
        await commendationDeleteDialog.clickOnConfirmButton();

        expect(await commendationComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});

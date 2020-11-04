import { browser, ExpectedConditions as ec /* , protractor, promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  FollowerComponentsPage,
  /* FollowerDeleteDialog, */
  FollowerUpdatePage,
} from './follower.page-object';

const expect = chai.expect;

describe('Follower e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let followerComponentsPage: FollowerComponentsPage;
  let followerUpdatePage: FollowerUpdatePage;
  /* let followerDeleteDialog: FollowerDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Followers', async () => {
    await navBarPage.goToEntity('follower');
    followerComponentsPage = new FollowerComponentsPage();
    await browser.wait(ec.visibilityOf(followerComponentsPage.title), 5000);
    expect(await followerComponentsPage.getTitle()).to.eq('shoppedApp.follower.home.title');
    await browser.wait(ec.or(ec.visibilityOf(followerComponentsPage.entities), ec.visibilityOf(followerComponentsPage.noResult)), 1000);
  });

  it('should load create Follower page', async () => {
    await followerComponentsPage.clickOnCreateButton();
    followerUpdatePage = new FollowerUpdatePage();
    expect(await followerUpdatePage.getPageTitle()).to.eq('shoppedApp.follower.home.createOrEditLabel');
    await followerUpdatePage.cancel();
  });

  /* it('should create and save Followers', async () => {
        const nbButtonsBeforeCreate = await followerComponentsPage.countDeleteButtons();

        await followerComponentsPage.clickOnCreateButton();

        await promise.all([
            followerUpdatePage.setCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            followerUpdatePage.statusSelectLastOption(),
            followerUpdatePage.userFollowedSelectLastOption(),
            followerUpdatePage.userSelectLastOption(),
        ]);

        expect(await followerUpdatePage.getCreatedInput()).to.contain('2001-01-01T02:30', 'Expected created value to be equals to 2000-12-31');

        await followerUpdatePage.save();
        expect(await followerUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await followerComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /* it('should delete last Follower', async () => {
        const nbButtonsBeforeDelete = await followerComponentsPage.countDeleteButtons();
        await followerComponentsPage.clickOnLastDeleteButton();

        followerDeleteDialog = new FollowerDeleteDialog();
        expect(await followerDeleteDialog.getDialogTitle())
            .to.eq('shoppedApp.follower.delete.question');
        await followerDeleteDialog.clickOnConfirmButton();

        expect(await followerComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});

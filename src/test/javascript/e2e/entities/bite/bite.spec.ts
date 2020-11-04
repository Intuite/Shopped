import { browser, ExpectedConditions as ec /* , protractor, promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  BiteComponentsPage,
  /* BiteDeleteDialog, */
  BiteUpdatePage,
} from './bite.page-object';

const expect = chai.expect;

describe('Bite e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let biteComponentsPage: BiteComponentsPage;
  let biteUpdatePage: BiteUpdatePage;
  /* let biteDeleteDialog: BiteDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Bites', async () => {
    await navBarPage.goToEntity('bite');
    biteComponentsPage = new BiteComponentsPage();
    await browser.wait(ec.visibilityOf(biteComponentsPage.title), 5000);
    expect(await biteComponentsPage.getTitle()).to.eq('shoppedApp.bite.home.title');
    await browser.wait(ec.or(ec.visibilityOf(biteComponentsPage.entities), ec.visibilityOf(biteComponentsPage.noResult)), 1000);
  });

  it('should load create Bite page', async () => {
    await biteComponentsPage.clickOnCreateButton();
    biteUpdatePage = new BiteUpdatePage();
    expect(await biteUpdatePage.getPageTitle()).to.eq('shoppedApp.bite.home.createOrEditLabel');
    await biteUpdatePage.cancel();
  });

  /* it('should create and save Bites', async () => {
        const nbButtonsBeforeCreate = await biteComponentsPage.countDeleteButtons();

        await biteComponentsPage.clickOnCreateButton();

        await promise.all([
            biteUpdatePage.setCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            biteUpdatePage.statusSelectLastOption(),
            biteUpdatePage.postSelectLastOption(),
            biteUpdatePage.userSelectLastOption(),
        ]);

        expect(await biteUpdatePage.getCreatedInput()).to.contain('2001-01-01T02:30', 'Expected created value to be equals to 2000-12-31');

        await biteUpdatePage.save();
        expect(await biteUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await biteComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /* it('should delete last Bite', async () => {
        const nbButtonsBeforeDelete = await biteComponentsPage.countDeleteButtons();
        await biteComponentsPage.clickOnLastDeleteButton();

        biteDeleteDialog = new BiteDeleteDialog();
        expect(await biteDeleteDialog.getDialogTitle())
            .to.eq('shoppedApp.bite.delete.question');
        await biteDeleteDialog.clickOnConfirmButton();

        expect(await biteComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});

import { browser, ExpectedConditions as ec /* , promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  CookiesComponentsPage,
  /* CookiesDeleteDialog, */
  CookiesUpdatePage,
} from './cookies.page-object';

const expect = chai.expect;

describe('Cookies e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let cookiesComponentsPage: CookiesComponentsPage;
  let cookiesUpdatePage: CookiesUpdatePage;
  /* let cookiesDeleteDialog: CookiesDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Cookies', async () => {
    await navBarPage.goToEntity('cookies');
    cookiesComponentsPage = new CookiesComponentsPage();
    await browser.wait(ec.visibilityOf(cookiesComponentsPage.title), 5000);
    expect(await cookiesComponentsPage.getTitle()).to.eq('shoppedApp.cookies.home.title');
    await browser.wait(ec.or(ec.visibilityOf(cookiesComponentsPage.entities), ec.visibilityOf(cookiesComponentsPage.noResult)), 1000);
  });

  it('should load create Cookies page', async () => {
    await cookiesComponentsPage.clickOnCreateButton();
    cookiesUpdatePage = new CookiesUpdatePage();
    expect(await cookiesUpdatePage.getPageTitle()).to.eq('shoppedApp.cookies.home.createOrEditLabel');
    await cookiesUpdatePage.cancel();
  });

  /* it('should create and save Cookies', async () => {
        const nbButtonsBeforeCreate = await cookiesComponentsPage.countDeleteButtons();

        await cookiesComponentsPage.clickOnCreateButton();

        await promise.all([
            cookiesUpdatePage.setAmountInput('5'),
            cookiesUpdatePage.setWalletKeyInput('walletKey'),
            cookiesUpdatePage.userSelectLastOption(),
        ]);

        expect(await cookiesUpdatePage.getAmountInput()).to.eq('5', 'Expected amount value to be equals to 5');
        expect(await cookiesUpdatePage.getWalletKeyInput()).to.eq('walletKey', 'Expected WalletKey value to be equals to walletKey');

        await cookiesUpdatePage.save();
        expect(await cookiesUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await cookiesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /* it('should delete last Cookies', async () => {
        const nbButtonsBeforeDelete = await cookiesComponentsPage.countDeleteButtons();
        await cookiesComponentsPage.clickOnLastDeleteButton();

        cookiesDeleteDialog = new CookiesDeleteDialog();
        expect(await cookiesDeleteDialog.getDialogTitle())
            .to.eq('shoppedApp.cookies.delete.question');
        await cookiesDeleteDialog.clickOnConfirmButton();

        expect(await cookiesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});

import { browser, ExpectedConditions as ec /* , protractor, promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  TransactionComponentsPage,
  /* TransactionDeleteDialog, */
  TransactionUpdatePage,
} from './transaction.page-object';

const expect = chai.expect;

describe('Transaction e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let transactionComponentsPage: TransactionComponentsPage;
  let transactionUpdatePage: TransactionUpdatePage;
  /* let transactionDeleteDialog: TransactionDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Transactions', async () => {
    await navBarPage.goToEntity('transaction');
    transactionComponentsPage = new TransactionComponentsPage();
    await browser.wait(ec.visibilityOf(transactionComponentsPage.title), 5000);
    expect(await transactionComponentsPage.getTitle()).to.eq('shoppedApp.transaction.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(transactionComponentsPage.entities), ec.visibilityOf(transactionComponentsPage.noResult)),
      1000
    );
  });

  it('should load create Transaction page', async () => {
    await transactionComponentsPage.clickOnCreateButton();
    transactionUpdatePage = new TransactionUpdatePage();
    expect(await transactionUpdatePage.getPageTitle()).to.eq('shoppedApp.transaction.home.createOrEditLabel');
    await transactionUpdatePage.cancel();
  });

  /* it('should create and save Transactions', async () => {
        const nbButtonsBeforeCreate = await transactionComponentsPage.countDeleteButtons();

        await transactionComponentsPage.clickOnCreateButton();

        await promise.all([
            transactionUpdatePage.setAmountInput('5'),
            transactionUpdatePage.setCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            transactionUpdatePage.setDescriptionInput('description'),
            transactionUpdatePage.setCookiesAmountInput('5'),
            transactionUpdatePage.userSelectLastOption(),
        ]);

        expect(await transactionUpdatePage.getAmountInput()).to.eq('5', 'Expected amount value to be equals to 5');
        expect(await transactionUpdatePage.getCreatedInput()).to.contain('2001-01-01T02:30', 'Expected created value to be equals to 2000-12-31');
        expect(await transactionUpdatePage.getDescriptionInput()).to.eq('description', 'Expected Description value to be equals to description');
        expect(await transactionUpdatePage.getCookiesAmountInput()).to.eq('5', 'Expected cookiesAmount value to be equals to 5');

        await transactionUpdatePage.save();
        expect(await transactionUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await transactionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /* it('should delete last Transaction', async () => {
        const nbButtonsBeforeDelete = await transactionComponentsPage.countDeleteButtons();
        await transactionComponentsPage.clickOnLastDeleteButton();

        transactionDeleteDialog = new TransactionDeleteDialog();
        expect(await transactionDeleteDialog.getDialogTitle())
            .to.eq('shoppedApp.transaction.delete.question');
        await transactionDeleteDialog.clickOnConfirmButton();

        expect(await transactionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});

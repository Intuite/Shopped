import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { BundleComponentsPage, BundleDeleteDialog, BundleUpdatePage } from './bundle.page-object';

const expect = chai.expect;

describe('Bundle e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let bundleComponentsPage: BundleComponentsPage;
  let bundleUpdatePage: BundleUpdatePage;
  let bundleDeleteDialog: BundleDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Bundles', async () => {
    await navBarPage.goToEntity('bundle');
    bundleComponentsPage = new BundleComponentsPage();
    await browser.wait(ec.visibilityOf(bundleComponentsPage.title), 5000);
    expect(await bundleComponentsPage.getTitle()).to.eq('shoppedApp.bundle.home.title');
    await browser.wait(ec.or(ec.visibilityOf(bundleComponentsPage.entities), ec.visibilityOf(bundleComponentsPage.noResult)), 1000);
  });

  it('should load create Bundle page', async () => {
    await bundleComponentsPage.clickOnCreateButton();
    bundleUpdatePage = new BundleUpdatePage();
    expect(await bundleUpdatePage.getPageTitle()).to.eq('shoppedApp.bundle.home.createOrEditLabel');
    await bundleUpdatePage.cancel();
  });

  it('should create and save Bundles', async () => {
    const nbButtonsBeforeCreate = await bundleComponentsPage.countDeleteButtons();

    await bundleComponentsPage.clickOnCreateButton();

    await promise.all([
      bundleUpdatePage.setNameInput('name'),
      bundleUpdatePage.setCostInput('5'),
      bundleUpdatePage.setCookieAmountInput('5'),
    ]);

    expect(await bundleUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await bundleUpdatePage.getCostInput()).to.eq('5', 'Expected cost value to be equals to 5');
    expect(await bundleUpdatePage.getCookieAmountInput()).to.eq('5', 'Expected cookieAmount value to be equals to 5');

    await bundleUpdatePage.save();
    expect(await bundleUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await bundleComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Bundle', async () => {
    const nbButtonsBeforeDelete = await bundleComponentsPage.countDeleteButtons();
    await bundleComponentsPage.clickOnLastDeleteButton();

    bundleDeleteDialog = new BundleDeleteDialog();
    expect(await bundleDeleteDialog.getDialogTitle()).to.eq('shoppedApp.bundle.delete.question');
    await bundleDeleteDialog.clickOnConfirmButton();

    expect(await bundleComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});

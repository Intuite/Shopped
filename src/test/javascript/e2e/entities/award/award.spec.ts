import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { AwardComponentsPage, AwardDeleteDialog, AwardUpdatePage } from './award.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('Award e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let awardComponentsPage: AwardComponentsPage;
  let awardUpdatePage: AwardUpdatePage;
  let awardDeleteDialog: AwardDeleteDialog;
  const fileNameToUpload = 'logo-jhipster.png';
  const fileToUpload = '../../../../../../src/main/webapp/content/images/' + fileNameToUpload;
  const absolutePath = path.resolve(__dirname, fileToUpload);

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Awards', async () => {
    await navBarPage.goToEntity('award');
    awardComponentsPage = new AwardComponentsPage();
    await browser.wait(ec.visibilityOf(awardComponentsPage.title), 5000);
    expect(await awardComponentsPage.getTitle()).to.eq('shoppedApp.award.home.title');
    await browser.wait(ec.or(ec.visibilityOf(awardComponentsPage.entities), ec.visibilityOf(awardComponentsPage.noResult)), 1000);
  });

  it('should load create Award page', async () => {
    await awardComponentsPage.clickOnCreateButton();
    awardUpdatePage = new AwardUpdatePage();
    expect(await awardUpdatePage.getPageTitle()).to.eq('shoppedApp.award.home.createOrEditLabel');
    await awardUpdatePage.cancel();
  });

  it('should create and save Awards', async () => {
    const nbButtonsBeforeCreate = await awardComponentsPage.countDeleteButtons();

    await awardComponentsPage.clickOnCreateButton();

    await promise.all([
      awardUpdatePage.setNameInput('name'),
      awardUpdatePage.setDescriptionInput('description'),
      awardUpdatePage.setCostInput('5'),
      awardUpdatePage.setImageInput(absolutePath),
      awardUpdatePage.statusSelectLastOption(),
    ]);

    expect(await awardUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await awardUpdatePage.getDescriptionInput()).to.eq('description', 'Expected Description value to be equals to description');
    expect(await awardUpdatePage.getCostInput()).to.eq('5', 'Expected cost value to be equals to 5');
    expect(await awardUpdatePage.getImageInput()).to.endsWith(fileNameToUpload, 'Expected Image value to be end with ' + fileNameToUpload);

    await awardUpdatePage.save();
    expect(await awardUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await awardComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Award', async () => {
    const nbButtonsBeforeDelete = await awardComponentsPage.countDeleteButtons();
    await awardComponentsPage.clickOnLastDeleteButton();

    awardDeleteDialog = new AwardDeleteDialog();
    expect(await awardDeleteDialog.getDialogTitle()).to.eq('shoppedApp.award.delete.question');
    await awardDeleteDialog.clickOnConfirmButton();

    expect(await awardComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});

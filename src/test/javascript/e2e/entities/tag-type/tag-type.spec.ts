import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { TagTypeComponentsPage, TagTypeDeleteDialog, TagTypeUpdatePage } from './tag-type.page-object';

const expect = chai.expect;

describe('TagType e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let tagTypeComponentsPage: TagTypeComponentsPage;
  let tagTypeUpdatePage: TagTypeUpdatePage;
  let tagTypeDeleteDialog: TagTypeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load TagTypes', async () => {
    await navBarPage.goToEntity('tag-type');
    tagTypeComponentsPage = new TagTypeComponentsPage();
    await browser.wait(ec.visibilityOf(tagTypeComponentsPage.title), 5000);
    expect(await tagTypeComponentsPage.getTitle()).to.eq('shoppedApp.tagType.home.title');
    await browser.wait(ec.or(ec.visibilityOf(tagTypeComponentsPage.entities), ec.visibilityOf(tagTypeComponentsPage.noResult)), 1000);
  });

  it('should load create TagType page', async () => {
    await tagTypeComponentsPage.clickOnCreateButton();
    tagTypeUpdatePage = new TagTypeUpdatePage();
    expect(await tagTypeUpdatePage.getPageTitle()).to.eq('shoppedApp.tagType.home.createOrEditLabel');
    await tagTypeUpdatePage.cancel();
  });

  it('should create and save TagTypes', async () => {
    const nbButtonsBeforeCreate = await tagTypeComponentsPage.countDeleteButtons();

    await tagTypeComponentsPage.clickOnCreateButton();

    await promise.all([
      tagTypeUpdatePage.setNameInput('name'),
      tagTypeUpdatePage.setDescriptionInput('description'),
      tagTypeUpdatePage.statusSelectLastOption(),
    ]);

    expect(await tagTypeUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await tagTypeUpdatePage.getDescriptionInput()).to.eq('description', 'Expected Description value to be equals to description');

    await tagTypeUpdatePage.save();
    expect(await tagTypeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await tagTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last TagType', async () => {
    const nbButtonsBeforeDelete = await tagTypeComponentsPage.countDeleteButtons();
    await tagTypeComponentsPage.clickOnLastDeleteButton();

    tagTypeDeleteDialog = new TagTypeDeleteDialog();
    expect(await tagTypeDeleteDialog.getDialogTitle()).to.eq('shoppedApp.tagType.delete.question');
    await tagTypeDeleteDialog.clickOnConfirmButton();

    expect(await tagTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});

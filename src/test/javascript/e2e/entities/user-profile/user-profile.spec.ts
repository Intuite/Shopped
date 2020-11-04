import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { UserProfileComponentsPage, UserProfileDeleteDialog, UserProfileUpdatePage } from './user-profile.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('UserProfile e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let userProfileComponentsPage: UserProfileComponentsPage;
  let userProfileUpdatePage: UserProfileUpdatePage;
  let userProfileDeleteDialog: UserProfileDeleteDialog;
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

  it('should load UserProfiles', async () => {
    await navBarPage.goToEntity('user-profile');
    userProfileComponentsPage = new UserProfileComponentsPage();
    await browser.wait(ec.visibilityOf(userProfileComponentsPage.title), 5000);
    expect(await userProfileComponentsPage.getTitle()).to.eq('shoppedApp.userProfile.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(userProfileComponentsPage.entities), ec.visibilityOf(userProfileComponentsPage.noResult)),
      1000
    );
  });

  it('should load create UserProfile page', async () => {
    await userProfileComponentsPage.clickOnCreateButton();
    userProfileUpdatePage = new UserProfileUpdatePage();
    expect(await userProfileUpdatePage.getPageTitle()).to.eq('shoppedApp.userProfile.home.createOrEditLabel');
    await userProfileUpdatePage.cancel();
  });

  it('should create and save UserProfiles', async () => {
    const nbButtonsBeforeCreate = await userProfileComponentsPage.countDeleteButtons();

    await userProfileComponentsPage.clickOnCreateButton();

    await promise.all([
      userProfileUpdatePage.setDescriptionInput('description'),
      userProfileUpdatePage.setBirthDateInput('2000-12-31'),
      userProfileUpdatePage.setImageInput(absolutePath),
      userProfileUpdatePage.statusSelectLastOption(),
      userProfileUpdatePage.userSelectLastOption(),
    ]);

    expect(await userProfileUpdatePage.getDescriptionInput()).to.eq(
      'description',
      'Expected Description value to be equals to description'
    );
    expect(await userProfileUpdatePage.getBirthDateInput()).to.eq('2000-12-31', 'Expected birthDate value to be equals to 2000-12-31');
    expect(await userProfileUpdatePage.getImageInput()).to.endsWith(
      fileNameToUpload,
      'Expected Image value to be end with ' + fileNameToUpload
    );

    await userProfileUpdatePage.save();
    expect(await userProfileUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await userProfileComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last UserProfile', async () => {
    const nbButtonsBeforeDelete = await userProfileComponentsPage.countDeleteButtons();
    await userProfileComponentsPage.clickOnLastDeleteButton();

    userProfileDeleteDialog = new UserProfileDeleteDialog();
    expect(await userProfileDeleteDialog.getDialogTitle()).to.eq('shoppedApp.userProfile.delete.question');
    await userProfileDeleteDialog.clickOnConfirmButton();

    expect(await userProfileComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});

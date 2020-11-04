import { browser, ExpectedConditions as ec /* , protractor, promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  CollectionComponentsPage,
  /* CollectionDeleteDialog, */
  CollectionUpdatePage,
} from './collection.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('Collection e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let collectionComponentsPage: CollectionComponentsPage;
  let collectionUpdatePage: CollectionUpdatePage;
  /* let collectionDeleteDialog: CollectionDeleteDialog; */
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

  it('should load Collections', async () => {
    await navBarPage.goToEntity('collection');
    collectionComponentsPage = new CollectionComponentsPage();
    await browser.wait(ec.visibilityOf(collectionComponentsPage.title), 5000);
    expect(await collectionComponentsPage.getTitle()).to.eq('shoppedApp.collection.home.title');
    await browser.wait(ec.or(ec.visibilityOf(collectionComponentsPage.entities), ec.visibilityOf(collectionComponentsPage.noResult)), 1000);
  });

  it('should load create Collection page', async () => {
    await collectionComponentsPage.clickOnCreateButton();
    collectionUpdatePage = new CollectionUpdatePage();
    expect(await collectionUpdatePage.getPageTitle()).to.eq('shoppedApp.collection.home.createOrEditLabel');
    await collectionUpdatePage.cancel();
  });

  /* it('should create and save Collections', async () => {
        const nbButtonsBeforeCreate = await collectionComponentsPage.countDeleteButtons();

        await collectionComponentsPage.clickOnCreateButton();

        await promise.all([
            collectionUpdatePage.setNameInput('name'),
            collectionUpdatePage.setDescriptionInput('description'),
            collectionUpdatePage.setCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            collectionUpdatePage.setImageInput(absolutePath),
            collectionUpdatePage.statusSelectLastOption(),
            collectionUpdatePage.userSelectLastOption(),
        ]);

        expect(await collectionUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
        expect(await collectionUpdatePage.getDescriptionInput()).to.eq('description', 'Expected Description value to be equals to description');
        expect(await collectionUpdatePage.getCreatedInput()).to.contain('2001-01-01T02:30', 'Expected created value to be equals to 2000-12-31');
        expect(await collectionUpdatePage.getImageInput()).to.endsWith(fileNameToUpload, 'Expected Image value to be end with ' + fileNameToUpload);

        await collectionUpdatePage.save();
        expect(await collectionUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await collectionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /* it('should delete last Collection', async () => {
        const nbButtonsBeforeDelete = await collectionComponentsPage.countDeleteButtons();
        await collectionComponentsPage.clickOnLastDeleteButton();

        collectionDeleteDialog = new CollectionDeleteDialog();
        expect(await collectionDeleteDialog.getDialogTitle())
            .to.eq('shoppedApp.collection.delete.question');
        await collectionDeleteDialog.clickOnConfirmButton();

        expect(await collectionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});

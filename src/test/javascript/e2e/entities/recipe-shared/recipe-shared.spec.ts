import { browser, ExpectedConditions as ec /* , promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  RecipeSharedComponentsPage,
  /* RecipeSharedDeleteDialog, */
  RecipeSharedUpdatePage,
} from './recipe-shared.page-object';

const expect = chai.expect;

describe('RecipeShared e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let recipeSharedComponentsPage: RecipeSharedComponentsPage;
  let recipeSharedUpdatePage: RecipeSharedUpdatePage;
  /* let recipeSharedDeleteDialog: RecipeSharedDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load RecipeShareds', async () => {
    await navBarPage.goToEntity('recipe-shared');
    recipeSharedComponentsPage = new RecipeSharedComponentsPage();
    await browser.wait(ec.visibilityOf(recipeSharedComponentsPage.title), 5000);
    expect(await recipeSharedComponentsPage.getTitle()).to.eq('shoppedApp.recipeShared.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(recipeSharedComponentsPage.entities), ec.visibilityOf(recipeSharedComponentsPage.noResult)),
      1000
    );
  });

  it('should load create RecipeShared page', async () => {
    await recipeSharedComponentsPage.clickOnCreateButton();
    recipeSharedUpdatePage = new RecipeSharedUpdatePage();
    expect(await recipeSharedUpdatePage.getPageTitle()).to.eq('shoppedApp.recipeShared.home.createOrEditLabel');
    await recipeSharedUpdatePage.cancel();
  });

  /* it('should create and save RecipeShareds', async () => {
        const nbButtonsBeforeCreate = await recipeSharedComponentsPage.countDeleteButtons();

        await recipeSharedComponentsPage.clickOnCreateButton();

        await promise.all([
            recipeSharedUpdatePage.statusSelectLastOption(),
            recipeSharedUpdatePage.recipeSelectLastOption(),
            recipeSharedUpdatePage.userSelectLastOption(),
        ]);


        await recipeSharedUpdatePage.save();
        expect(await recipeSharedUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await recipeSharedComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /* it('should delete last RecipeShared', async () => {
        const nbButtonsBeforeDelete = await recipeSharedComponentsPage.countDeleteButtons();
        await recipeSharedComponentsPage.clickOnLastDeleteButton();

        recipeSharedDeleteDialog = new RecipeSharedDeleteDialog();
        expect(await recipeSharedDeleteDialog.getDialogTitle())
            .to.eq('shoppedApp.recipeShared.delete.question');
        await recipeSharedDeleteDialog.clickOnConfirmButton();

        expect(await recipeSharedComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});

import { browser, ExpectedConditions as ec /* , promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  RecipeHasRecipeTagComponentsPage,
  /* RecipeHasRecipeTagDeleteDialog, */
  RecipeHasRecipeTagUpdatePage,
} from './recipe-has-recipe-tag.page-object';

const expect = chai.expect;

describe('RecipeHasRecipeTag e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let recipeHasRecipeTagComponentsPage: RecipeHasRecipeTagComponentsPage;
  let recipeHasRecipeTagUpdatePage: RecipeHasRecipeTagUpdatePage;
  /* let recipeHasRecipeTagDeleteDialog: RecipeHasRecipeTagDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load RecipeHasRecipeTags', async () => {
    await navBarPage.goToEntity('recipe-has-recipe-tag');
    recipeHasRecipeTagComponentsPage = new RecipeHasRecipeTagComponentsPage();
    await browser.wait(ec.visibilityOf(recipeHasRecipeTagComponentsPage.title), 5000);
    expect(await recipeHasRecipeTagComponentsPage.getTitle()).to.eq('shoppedApp.recipeHasRecipeTag.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(recipeHasRecipeTagComponentsPage.entities), ec.visibilityOf(recipeHasRecipeTagComponentsPage.noResult)),
      1000
    );
  });

  it('should load create RecipeHasRecipeTag page', async () => {
    await recipeHasRecipeTagComponentsPage.clickOnCreateButton();
    recipeHasRecipeTagUpdatePage = new RecipeHasRecipeTagUpdatePage();
    expect(await recipeHasRecipeTagUpdatePage.getPageTitle()).to.eq('shoppedApp.recipeHasRecipeTag.home.createOrEditLabel');
    await recipeHasRecipeTagUpdatePage.cancel();
  });

  /* it('should create and save RecipeHasRecipeTags', async () => {
        const nbButtonsBeforeCreate = await recipeHasRecipeTagComponentsPage.countDeleteButtons();

        await recipeHasRecipeTagComponentsPage.clickOnCreateButton();

        await promise.all([
            recipeHasRecipeTagUpdatePage.statusSelectLastOption(),
            recipeHasRecipeTagUpdatePage.recipeSelectLastOption(),
            recipeHasRecipeTagUpdatePage.recipeTagSelectLastOption(),
        ]);


        await recipeHasRecipeTagUpdatePage.save();
        expect(await recipeHasRecipeTagUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await recipeHasRecipeTagComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /* it('should delete last RecipeHasRecipeTag', async () => {
        const nbButtonsBeforeDelete = await recipeHasRecipeTagComponentsPage.countDeleteButtons();
        await recipeHasRecipeTagComponentsPage.clickOnLastDeleteButton();

        recipeHasRecipeTagDeleteDialog = new RecipeHasRecipeTagDeleteDialog();
        expect(await recipeHasRecipeTagDeleteDialog.getDialogTitle())
            .to.eq('shoppedApp.recipeHasRecipeTag.delete.question');
        await recipeHasRecipeTagDeleteDialog.clickOnConfirmButton();

        expect(await recipeHasRecipeTagComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});

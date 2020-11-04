import { browser, ExpectedConditions as ec /* , promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  CollectionHasRecipeComponentsPage,
  /* CollectionHasRecipeDeleteDialog, */
  CollectionHasRecipeUpdatePage,
} from './collection-has-recipe.page-object';

const expect = chai.expect;

describe('CollectionHasRecipe e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let collectionHasRecipeComponentsPage: CollectionHasRecipeComponentsPage;
  let collectionHasRecipeUpdatePage: CollectionHasRecipeUpdatePage;
  /* let collectionHasRecipeDeleteDialog: CollectionHasRecipeDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load CollectionHasRecipes', async () => {
    await navBarPage.goToEntity('collection-has-recipe');
    collectionHasRecipeComponentsPage = new CollectionHasRecipeComponentsPage();
    await browser.wait(ec.visibilityOf(collectionHasRecipeComponentsPage.title), 5000);
    expect(await collectionHasRecipeComponentsPage.getTitle()).to.eq('shoppedApp.collectionHasRecipe.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(collectionHasRecipeComponentsPage.entities), ec.visibilityOf(collectionHasRecipeComponentsPage.noResult)),
      1000
    );
  });

  it('should load create CollectionHasRecipe page', async () => {
    await collectionHasRecipeComponentsPage.clickOnCreateButton();
    collectionHasRecipeUpdatePage = new CollectionHasRecipeUpdatePage();
    expect(await collectionHasRecipeUpdatePage.getPageTitle()).to.eq('shoppedApp.collectionHasRecipe.home.createOrEditLabel');
    await collectionHasRecipeUpdatePage.cancel();
  });

  /* it('should create and save CollectionHasRecipes', async () => {
        const nbButtonsBeforeCreate = await collectionHasRecipeComponentsPage.countDeleteButtons();

        await collectionHasRecipeComponentsPage.clickOnCreateButton();

        await promise.all([
            collectionHasRecipeUpdatePage.statusSelectLastOption(),
            collectionHasRecipeUpdatePage.collectionSelectLastOption(),
            collectionHasRecipeUpdatePage.recipeSelectLastOption(),
        ]);


        await collectionHasRecipeUpdatePage.save();
        expect(await collectionHasRecipeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await collectionHasRecipeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /* it('should delete last CollectionHasRecipe', async () => {
        const nbButtonsBeforeDelete = await collectionHasRecipeComponentsPage.countDeleteButtons();
        await collectionHasRecipeComponentsPage.clickOnLastDeleteButton();

        collectionHasRecipeDeleteDialog = new CollectionHasRecipeDeleteDialog();
        expect(await collectionHasRecipeDeleteDialog.getDialogTitle())
            .to.eq('shoppedApp.collectionHasRecipe.delete.question');
        await collectionHasRecipeDeleteDialog.clickOnConfirmButton();

        expect(await collectionHasRecipeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});

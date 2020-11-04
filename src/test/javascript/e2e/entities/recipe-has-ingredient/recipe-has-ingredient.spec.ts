import { browser, ExpectedConditions as ec /* , promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  RecipeHasIngredientComponentsPage,
  /* RecipeHasIngredientDeleteDialog, */
  RecipeHasIngredientUpdatePage,
} from './recipe-has-ingredient.page-object';

const expect = chai.expect;

describe('RecipeHasIngredient e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let recipeHasIngredientComponentsPage: RecipeHasIngredientComponentsPage;
  let recipeHasIngredientUpdatePage: RecipeHasIngredientUpdatePage;
  /* let recipeHasIngredientDeleteDialog: RecipeHasIngredientDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load RecipeHasIngredients', async () => {
    await navBarPage.goToEntity('recipe-has-ingredient');
    recipeHasIngredientComponentsPage = new RecipeHasIngredientComponentsPage();
    await browser.wait(ec.visibilityOf(recipeHasIngredientComponentsPage.title), 5000);
    expect(await recipeHasIngredientComponentsPage.getTitle()).to.eq('shoppedApp.recipeHasIngredient.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(recipeHasIngredientComponentsPage.entities), ec.visibilityOf(recipeHasIngredientComponentsPage.noResult)),
      1000
    );
  });

  it('should load create RecipeHasIngredient page', async () => {
    await recipeHasIngredientComponentsPage.clickOnCreateButton();
    recipeHasIngredientUpdatePage = new RecipeHasIngredientUpdatePage();
    expect(await recipeHasIngredientUpdatePage.getPageTitle()).to.eq('shoppedApp.recipeHasIngredient.home.createOrEditLabel');
    await recipeHasIngredientUpdatePage.cancel();
  });

  /* it('should create and save RecipeHasIngredients', async () => {
        const nbButtonsBeforeCreate = await recipeHasIngredientComponentsPage.countDeleteButtons();

        await recipeHasIngredientComponentsPage.clickOnCreateButton();

        await promise.all([
            recipeHasIngredientUpdatePage.setAmountInput('5'),
            recipeHasIngredientUpdatePage.statusSelectLastOption(),
            recipeHasIngredientUpdatePage.ingredientSelectLastOption(),
            recipeHasIngredientUpdatePage.recipeSelectLastOption(),
        ]);

        expect(await recipeHasIngredientUpdatePage.getAmountInput()).to.eq('5', 'Expected amount value to be equals to 5');

        await recipeHasIngredientUpdatePage.save();
        expect(await recipeHasIngredientUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await recipeHasIngredientComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /* it('should delete last RecipeHasIngredient', async () => {
        const nbButtonsBeforeDelete = await recipeHasIngredientComponentsPage.countDeleteButtons();
        await recipeHasIngredientComponentsPage.clickOnLastDeleteButton();

        recipeHasIngredientDeleteDialog = new RecipeHasIngredientDeleteDialog();
        expect(await recipeHasIngredientDeleteDialog.getDialogTitle())
            .to.eq('shoppedApp.recipeHasIngredient.delete.question');
        await recipeHasIngredientDeleteDialog.clickOnConfirmButton();

        expect(await recipeHasIngredientComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});

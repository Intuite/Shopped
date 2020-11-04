import { browser, ExpectedConditions as ec /* , promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  CartHasRecipeComponentsPage,
  /* CartHasRecipeDeleteDialog, */
  CartHasRecipeUpdatePage,
} from './cart-has-recipe.page-object';

const expect = chai.expect;

describe('CartHasRecipe e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let cartHasRecipeComponentsPage: CartHasRecipeComponentsPage;
  let cartHasRecipeUpdatePage: CartHasRecipeUpdatePage;
  /* let cartHasRecipeDeleteDialog: CartHasRecipeDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load CartHasRecipes', async () => {
    await navBarPage.goToEntity('cart-has-recipe');
    cartHasRecipeComponentsPage = new CartHasRecipeComponentsPage();
    await browser.wait(ec.visibilityOf(cartHasRecipeComponentsPage.title), 5000);
    expect(await cartHasRecipeComponentsPage.getTitle()).to.eq('shoppedApp.cartHasRecipe.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(cartHasRecipeComponentsPage.entities), ec.visibilityOf(cartHasRecipeComponentsPage.noResult)),
      1000
    );
  });

  it('should load create CartHasRecipe page', async () => {
    await cartHasRecipeComponentsPage.clickOnCreateButton();
    cartHasRecipeUpdatePage = new CartHasRecipeUpdatePage();
    expect(await cartHasRecipeUpdatePage.getPageTitle()).to.eq('shoppedApp.cartHasRecipe.home.createOrEditLabel');
    await cartHasRecipeUpdatePage.cancel();
  });

  /* it('should create and save CartHasRecipes', async () => {
        const nbButtonsBeforeCreate = await cartHasRecipeComponentsPage.countDeleteButtons();

        await cartHasRecipeComponentsPage.clickOnCreateButton();

        await promise.all([
            cartHasRecipeUpdatePage.statusSelectLastOption(),
            cartHasRecipeUpdatePage.recipeSelectLastOption(),
            cartHasRecipeUpdatePage.cartSelectLastOption(),
        ]);


        await cartHasRecipeUpdatePage.save();
        expect(await cartHasRecipeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await cartHasRecipeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /* it('should delete last CartHasRecipe', async () => {
        const nbButtonsBeforeDelete = await cartHasRecipeComponentsPage.countDeleteButtons();
        await cartHasRecipeComponentsPage.clickOnLastDeleteButton();

        cartHasRecipeDeleteDialog = new CartHasRecipeDeleteDialog();
        expect(await cartHasRecipeDeleteDialog.getDialogTitle())
            .to.eq('shoppedApp.cartHasRecipe.delete.question');
        await cartHasRecipeDeleteDialog.clickOnConfirmButton();

        expect(await cartHasRecipeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});

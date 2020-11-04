import { browser, ExpectedConditions as ec /* , promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  CartHasIngredientComponentsPage,
  /* CartHasIngredientDeleteDialog, */
  CartHasIngredientUpdatePage,
} from './cart-has-ingredient.page-object';

const expect = chai.expect;

describe('CartHasIngredient e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let cartHasIngredientComponentsPage: CartHasIngredientComponentsPage;
  let cartHasIngredientUpdatePage: CartHasIngredientUpdatePage;
  /* let cartHasIngredientDeleteDialog: CartHasIngredientDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load CartHasIngredients', async () => {
    await navBarPage.goToEntity('cart-has-ingredient');
    cartHasIngredientComponentsPage = new CartHasIngredientComponentsPage();
    await browser.wait(ec.visibilityOf(cartHasIngredientComponentsPage.title), 5000);
    expect(await cartHasIngredientComponentsPage.getTitle()).to.eq('shoppedApp.cartHasIngredient.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(cartHasIngredientComponentsPage.entities), ec.visibilityOf(cartHasIngredientComponentsPage.noResult)),
      1000
    );
  });

  it('should load create CartHasIngredient page', async () => {
    await cartHasIngredientComponentsPage.clickOnCreateButton();
    cartHasIngredientUpdatePage = new CartHasIngredientUpdatePage();
    expect(await cartHasIngredientUpdatePage.getPageTitle()).to.eq('shoppedApp.cartHasIngredient.home.createOrEditLabel');
    await cartHasIngredientUpdatePage.cancel();
  });

  /* it('should create and save CartHasIngredients', async () => {
        const nbButtonsBeforeCreate = await cartHasIngredientComponentsPage.countDeleteButtons();

        await cartHasIngredientComponentsPage.clickOnCreateButton();

        await promise.all([
            cartHasIngredientUpdatePage.setAmountInput('5'),
            cartHasIngredientUpdatePage.statusSelectLastOption(),
            cartHasIngredientUpdatePage.cartSelectLastOption(),
            cartHasIngredientUpdatePage.ingredientSelectLastOption(),
        ]);

        expect(await cartHasIngredientUpdatePage.getAmountInput()).to.eq('5', 'Expected amount value to be equals to 5');

        await cartHasIngredientUpdatePage.save();
        expect(await cartHasIngredientUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await cartHasIngredientComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /* it('should delete last CartHasIngredient', async () => {
        const nbButtonsBeforeDelete = await cartHasIngredientComponentsPage.countDeleteButtons();
        await cartHasIngredientComponentsPage.clickOnLastDeleteButton();

        cartHasIngredientDeleteDialog = new CartHasIngredientDeleteDialog();
        expect(await cartHasIngredientDeleteDialog.getDialogTitle())
            .to.eq('shoppedApp.cartHasIngredient.delete.question');
        await cartHasIngredientDeleteDialog.clickOnConfirmButton();

        expect(await cartHasIngredientComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});

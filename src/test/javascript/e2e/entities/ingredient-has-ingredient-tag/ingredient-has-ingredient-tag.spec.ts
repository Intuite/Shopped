import { browser, ExpectedConditions as ec /* , promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  IngredientHasIngredientTagComponentsPage,
  /* IngredientHasIngredientTagDeleteDialog, */
  IngredientHasIngredientTagUpdatePage,
} from './ingredient-has-ingredient-tag.page-object';

const expect = chai.expect;

describe('IngredientHasIngredientTag e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let ingredientHasIngredientTagComponentsPage: IngredientHasIngredientTagComponentsPage;
  let ingredientHasIngredientTagUpdatePage: IngredientHasIngredientTagUpdatePage;
  /* let ingredientHasIngredientTagDeleteDialog: IngredientHasIngredientTagDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load IngredientHasIngredientTags', async () => {
    await navBarPage.goToEntity('ingredient-has-ingredient-tag');
    ingredientHasIngredientTagComponentsPage = new IngredientHasIngredientTagComponentsPage();
    await browser.wait(ec.visibilityOf(ingredientHasIngredientTagComponentsPage.title), 5000);
    expect(await ingredientHasIngredientTagComponentsPage.getTitle()).to.eq('shoppedApp.ingredientHasIngredientTag.home.title');
    await browser.wait(
      ec.or(
        ec.visibilityOf(ingredientHasIngredientTagComponentsPage.entities),
        ec.visibilityOf(ingredientHasIngredientTagComponentsPage.noResult)
      ),
      1000
    );
  });

  it('should load create IngredientHasIngredientTag page', async () => {
    await ingredientHasIngredientTagComponentsPage.clickOnCreateButton();
    ingredientHasIngredientTagUpdatePage = new IngredientHasIngredientTagUpdatePage();
    expect(await ingredientHasIngredientTagUpdatePage.getPageTitle()).to.eq('shoppedApp.ingredientHasIngredientTag.home.createOrEditLabel');
    await ingredientHasIngredientTagUpdatePage.cancel();
  });

  /* it('should create and save IngredientHasIngredientTags', async () => {
        const nbButtonsBeforeCreate = await ingredientHasIngredientTagComponentsPage.countDeleteButtons();

        await ingredientHasIngredientTagComponentsPage.clickOnCreateButton();

        await promise.all([
            ingredientHasIngredientTagUpdatePage.statusSelectLastOption(),
            ingredientHasIngredientTagUpdatePage.ingredientSelectLastOption(),
            ingredientHasIngredientTagUpdatePage.ingredientTagSelectLastOption(),
        ]);


        await ingredientHasIngredientTagUpdatePage.save();
        expect(await ingredientHasIngredientTagUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await ingredientHasIngredientTagComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /* it('should delete last IngredientHasIngredientTag', async () => {
        const nbButtonsBeforeDelete = await ingredientHasIngredientTagComponentsPage.countDeleteButtons();
        await ingredientHasIngredientTagComponentsPage.clickOnLastDeleteButton();

        ingredientHasIngredientTagDeleteDialog = new IngredientHasIngredientTagDeleteDialog();
        expect(await ingredientHasIngredientTagDeleteDialog.getDialogTitle())
            .to.eq('shoppedApp.ingredientHasIngredientTag.delete.question');
        await ingredientHasIngredientTagDeleteDialog.clickOnConfirmButton();

        expect(await ingredientHasIngredientTagComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});

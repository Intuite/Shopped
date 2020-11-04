import { browser, ExpectedConditions as ec /* , promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  IngredientTagComponentsPage,
  /* IngredientTagDeleteDialog, */
  IngredientTagUpdatePage,
} from './ingredient-tag.page-object';

const expect = chai.expect;

describe('IngredientTag e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let ingredientTagComponentsPage: IngredientTagComponentsPage;
  let ingredientTagUpdatePage: IngredientTagUpdatePage;
  /* let ingredientTagDeleteDialog: IngredientTagDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load IngredientTags', async () => {
    await navBarPage.goToEntity('ingredient-tag');
    ingredientTagComponentsPage = new IngredientTagComponentsPage();
    await browser.wait(ec.visibilityOf(ingredientTagComponentsPage.title), 5000);
    expect(await ingredientTagComponentsPage.getTitle()).to.eq('shoppedApp.ingredientTag.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(ingredientTagComponentsPage.entities), ec.visibilityOf(ingredientTagComponentsPage.noResult)),
      1000
    );
  });

  it('should load create IngredientTag page', async () => {
    await ingredientTagComponentsPage.clickOnCreateButton();
    ingredientTagUpdatePage = new IngredientTagUpdatePage();
    expect(await ingredientTagUpdatePage.getPageTitle()).to.eq('shoppedApp.ingredientTag.home.createOrEditLabel');
    await ingredientTagUpdatePage.cancel();
  });

  /* it('should create and save IngredientTags', async () => {
        const nbButtonsBeforeCreate = await ingredientTagComponentsPage.countDeleteButtons();

        await ingredientTagComponentsPage.clickOnCreateButton();

        await promise.all([
            ingredientTagUpdatePage.setNameInput('name'),
            ingredientTagUpdatePage.setDescriptionInput('description'),
            ingredientTagUpdatePage.statusSelectLastOption(),
            ingredientTagUpdatePage.typeSelectLastOption(),
        ]);

        expect(await ingredientTagUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
        expect(await ingredientTagUpdatePage.getDescriptionInput()).to.eq('description', 'Expected Description value to be equals to description');

        await ingredientTagUpdatePage.save();
        expect(await ingredientTagUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await ingredientTagComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /* it('should delete last IngredientTag', async () => {
        const nbButtonsBeforeDelete = await ingredientTagComponentsPage.countDeleteButtons();
        await ingredientTagComponentsPage.clickOnLastDeleteButton();

        ingredientTagDeleteDialog = new IngredientTagDeleteDialog();
        expect(await ingredientTagDeleteDialog.getDialogTitle())
            .to.eq('shoppedApp.ingredientTag.delete.question');
        await ingredientTagDeleteDialog.clickOnConfirmButton();

        expect(await ingredientTagComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});

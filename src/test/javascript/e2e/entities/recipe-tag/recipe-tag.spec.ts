import { browser, ExpectedConditions as ec /* , promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  RecipeTagComponentsPage,
  /* RecipeTagDeleteDialog, */
  RecipeTagUpdatePage,
} from './recipe-tag.page-object';

const expect = chai.expect;

describe('RecipeTag e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let recipeTagComponentsPage: RecipeTagComponentsPage;
  let recipeTagUpdatePage: RecipeTagUpdatePage;
  /* let recipeTagDeleteDialog: RecipeTagDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load RecipeTags', async () => {
    await navBarPage.goToEntity('recipe-tag');
    recipeTagComponentsPage = new RecipeTagComponentsPage();
    await browser.wait(ec.visibilityOf(recipeTagComponentsPage.title), 5000);
    expect(await recipeTagComponentsPage.getTitle()).to.eq('shoppedApp.recipeTag.home.title');
    await browser.wait(ec.or(ec.visibilityOf(recipeTagComponentsPage.entities), ec.visibilityOf(recipeTagComponentsPage.noResult)), 1000);
  });

  it('should load create RecipeTag page', async () => {
    await recipeTagComponentsPage.clickOnCreateButton();
    recipeTagUpdatePage = new RecipeTagUpdatePage();
    expect(await recipeTagUpdatePage.getPageTitle()).to.eq('shoppedApp.recipeTag.home.createOrEditLabel');
    await recipeTagUpdatePage.cancel();
  });

  /* it('should create and save RecipeTags', async () => {
        const nbButtonsBeforeCreate = await recipeTagComponentsPage.countDeleteButtons();

        await recipeTagComponentsPage.clickOnCreateButton();

        await promise.all([
            recipeTagUpdatePage.setNameInput('name'),
            recipeTagUpdatePage.setDescriptionInput('description'),
            recipeTagUpdatePage.statusSelectLastOption(),
            recipeTagUpdatePage.typeSelectLastOption(),
        ]);

        expect(await recipeTagUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
        expect(await recipeTagUpdatePage.getDescriptionInput()).to.eq('description', 'Expected Description value to be equals to description');

        await recipeTagUpdatePage.save();
        expect(await recipeTagUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await recipeTagComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /* it('should delete last RecipeTag', async () => {
        const nbButtonsBeforeDelete = await recipeTagComponentsPage.countDeleteButtons();
        await recipeTagComponentsPage.clickOnLastDeleteButton();

        recipeTagDeleteDialog = new RecipeTagDeleteDialog();
        expect(await recipeTagDeleteDialog.getDialogTitle())
            .to.eq('shoppedApp.recipeTag.delete.question');
        await recipeTagDeleteDialog.clickOnConfirmButton();

        expect(await recipeTagComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});

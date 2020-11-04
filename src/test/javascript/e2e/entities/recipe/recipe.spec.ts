import { browser, ExpectedConditions as ec /* , protractor, promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  RecipeComponentsPage,
  /* RecipeDeleteDialog, */
  RecipeUpdatePage,
} from './recipe.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('Recipe e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let recipeComponentsPage: RecipeComponentsPage;
  let recipeUpdatePage: RecipeUpdatePage;
  /* let recipeDeleteDialog: RecipeDeleteDialog; */
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

  it('should load Recipes', async () => {
    await navBarPage.goToEntity('recipe');
    recipeComponentsPage = new RecipeComponentsPage();
    await browser.wait(ec.visibilityOf(recipeComponentsPage.title), 5000);
    expect(await recipeComponentsPage.getTitle()).to.eq('shoppedApp.recipe.home.title');
    await browser.wait(ec.or(ec.visibilityOf(recipeComponentsPage.entities), ec.visibilityOf(recipeComponentsPage.noResult)), 1000);
  });

  it('should load create Recipe page', async () => {
    await recipeComponentsPage.clickOnCreateButton();
    recipeUpdatePage = new RecipeUpdatePage();
    expect(await recipeUpdatePage.getPageTitle()).to.eq('shoppedApp.recipe.home.createOrEditLabel');
    await recipeUpdatePage.cancel();
  });

  /* it('should create and save Recipes', async () => {
        const nbButtonsBeforeCreate = await recipeComponentsPage.countDeleteButtons();

        await recipeComponentsPage.clickOnCreateButton();

        await promise.all([
            recipeUpdatePage.setNameInput('name'),
            recipeUpdatePage.setPortionInput('5'),
            recipeUpdatePage.setDescriptionInput('description'),
            recipeUpdatePage.setDurationInput('5'),
            recipeUpdatePage.setCreationInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            recipeUpdatePage.setImageInput(absolutePath),
            recipeUpdatePage.statusSelectLastOption(),
            recipeUpdatePage.userSelectLastOption(),
        ]);

        expect(await recipeUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
        expect(await recipeUpdatePage.getPortionInput()).to.eq('5', 'Expected portion value to be equals to 5');
        expect(await recipeUpdatePage.getDescriptionInput()).to.eq('description', 'Expected Description value to be equals to description');
        expect(await recipeUpdatePage.getDurationInput()).to.eq('5', 'Expected duration value to be equals to 5');
        expect(await recipeUpdatePage.getCreationInput()).to.contain('2001-01-01T02:30', 'Expected creation value to be equals to 2000-12-31');
        expect(await recipeUpdatePage.getImageInput()).to.endsWith(fileNameToUpload, 'Expected Image value to be end with ' + fileNameToUpload);

        await recipeUpdatePage.save();
        expect(await recipeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await recipeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /* it('should delete last Recipe', async () => {
        const nbButtonsBeforeDelete = await recipeComponentsPage.countDeleteButtons();
        await recipeComponentsPage.clickOnLastDeleteButton();

        recipeDeleteDialog = new RecipeDeleteDialog();
        expect(await recipeDeleteDialog.getDialogTitle())
            .to.eq('shoppedApp.recipe.delete.question');
        await recipeDeleteDialog.clickOnConfirmButton();

        expect(await recipeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});

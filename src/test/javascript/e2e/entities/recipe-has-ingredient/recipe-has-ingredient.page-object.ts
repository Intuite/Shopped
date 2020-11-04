import { element, by, ElementFinder } from 'protractor';

export class RecipeHasIngredientComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-recipe-has-ingredient div table .btn-danger'));
  title = element.all(by.css('jhi-recipe-has-ingredient div h2#page-heading span')).first();
  noResult = element(by.id('no-result'));
  entities = element(by.id('entities'));

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class RecipeHasIngredientUpdatePage {
  pageTitle = element(by.id('jhi-recipe-has-ingredient-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  amountInput = element(by.id('field_amount'));
  statusSelect = element(by.id('field_status'));

  ingredientSelect = element(by.id('field_ingredient'));
  recipeSelect = element(by.id('field_recipe'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setAmountInput(amount: string): Promise<void> {
    await this.amountInput.sendKeys(amount);
  }

  async getAmountInput(): Promise<string> {
    return await this.amountInput.getAttribute('value');
  }

  async setStatusSelect(status: string): Promise<void> {
    await this.statusSelect.sendKeys(status);
  }

  async getStatusSelect(): Promise<string> {
    return await this.statusSelect.element(by.css('option:checked')).getText();
  }

  async statusSelectLastOption(): Promise<void> {
    await this.statusSelect.all(by.tagName('option')).last().click();
  }

  async ingredientSelectLastOption(): Promise<void> {
    await this.ingredientSelect.all(by.tagName('option')).last().click();
  }

  async ingredientSelectOption(option: string): Promise<void> {
    await this.ingredientSelect.sendKeys(option);
  }

  getIngredientSelect(): ElementFinder {
    return this.ingredientSelect;
  }

  async getIngredientSelectedOption(): Promise<string> {
    return await this.ingredientSelect.element(by.css('option:checked')).getText();
  }

  async recipeSelectLastOption(): Promise<void> {
    await this.recipeSelect.all(by.tagName('option')).last().click();
  }

  async recipeSelectOption(option: string): Promise<void> {
    await this.recipeSelect.sendKeys(option);
  }

  getRecipeSelect(): ElementFinder {
    return this.recipeSelect;
  }

  async getRecipeSelectedOption(): Promise<string> {
    return await this.recipeSelect.element(by.css('option:checked')).getText();
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class RecipeHasIngredientDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-recipeHasIngredient-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-recipeHasIngredient'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}

import { element, by, ElementFinder } from 'protractor';

export class RecipeHasRecipeTagComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-recipe-has-recipe-tag div table .btn-danger'));
  title = element.all(by.css('jhi-recipe-has-recipe-tag div h2#page-heading span')).first();
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

export class RecipeHasRecipeTagUpdatePage {
  pageTitle = element(by.id('jhi-recipe-has-recipe-tag-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  statusSelect = element(by.id('field_status'));

  recipeSelect = element(by.id('field_recipe'));
  recipeTagSelect = element(by.id('field_recipeTag'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
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

  async recipeTagSelectLastOption(): Promise<void> {
    await this.recipeTagSelect.all(by.tagName('option')).last().click();
  }

  async recipeTagSelectOption(option: string): Promise<void> {
    await this.recipeTagSelect.sendKeys(option);
  }

  getRecipeTagSelect(): ElementFinder {
    return this.recipeTagSelect;
  }

  async getRecipeTagSelectedOption(): Promise<string> {
    return await this.recipeTagSelect.element(by.css('option:checked')).getText();
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

export class RecipeHasRecipeTagDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-recipeHasRecipeTag-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-recipeHasRecipeTag'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}

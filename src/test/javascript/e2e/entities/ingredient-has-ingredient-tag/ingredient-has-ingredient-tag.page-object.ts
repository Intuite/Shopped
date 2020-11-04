import { element, by, ElementFinder } from 'protractor';

export class IngredientHasIngredientTagComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-ingredient-has-ingredient-tag div table .btn-danger'));
  title = element.all(by.css('jhi-ingredient-has-ingredient-tag div h2#page-heading span')).first();
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

export class IngredientHasIngredientTagUpdatePage {
  pageTitle = element(by.id('jhi-ingredient-has-ingredient-tag-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  statusSelect = element(by.id('field_status'));

  ingredientSelect = element(by.id('field_ingredient'));
  ingredientTagSelect = element(by.id('field_ingredientTag'));

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

  async ingredientTagSelectLastOption(): Promise<void> {
    await this.ingredientTagSelect.all(by.tagName('option')).last().click();
  }

  async ingredientTagSelectOption(option: string): Promise<void> {
    await this.ingredientTagSelect.sendKeys(option);
  }

  getIngredientTagSelect(): ElementFinder {
    return this.ingredientTagSelect;
  }

  async getIngredientTagSelectedOption(): Promise<string> {
    return await this.ingredientTagSelect.element(by.css('option:checked')).getText();
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

export class IngredientHasIngredientTagDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-ingredientHasIngredientTag-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-ingredientHasIngredientTag'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}

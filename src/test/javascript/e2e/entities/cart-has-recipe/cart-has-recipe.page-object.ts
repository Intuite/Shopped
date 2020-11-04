import { element, by, ElementFinder } from 'protractor';

export class CartHasRecipeComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-cart-has-recipe div table .btn-danger'));
  title = element.all(by.css('jhi-cart-has-recipe div h2#page-heading span')).first();
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

export class CartHasRecipeUpdatePage {
  pageTitle = element(by.id('jhi-cart-has-recipe-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  statusSelect = element(by.id('field_status'));

  recipeSelect = element(by.id('field_recipe'));
  cartSelect = element(by.id('field_cart'));

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

  async cartSelectLastOption(): Promise<void> {
    await this.cartSelect.all(by.tagName('option')).last().click();
  }

  async cartSelectOption(option: string): Promise<void> {
    await this.cartSelect.sendKeys(option);
  }

  getCartSelect(): ElementFinder {
    return this.cartSelect;
  }

  async getCartSelectedOption(): Promise<string> {
    return await this.cartSelect.element(by.css('option:checked')).getText();
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

export class CartHasRecipeDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-cartHasRecipe-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-cartHasRecipe'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}

import { element, by, ElementFinder } from 'protractor';

export class PostComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-post div table .btn-danger'));
  title = element.all(by.css('jhi-post div h2#page-heading span')).first();
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

export class PostUpdatePage {
  pageTitle = element(by.id('jhi-post-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  captionInput = element(by.id('field_caption'));
  dateInput = element(by.id('field_date'));
  statusSelect = element(by.id('field_status'));

  recipeSelect = element(by.id('field_recipe'));
  userSelect = element(by.id('field_user'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setCaptionInput(caption: string): Promise<void> {
    await this.captionInput.sendKeys(caption);
  }

  async getCaptionInput(): Promise<string> {
    return await this.captionInput.getAttribute('value');
  }

  async setDateInput(date: string): Promise<void> {
    await this.dateInput.sendKeys(date);
  }

  async getDateInput(): Promise<string> {
    return await this.dateInput.getAttribute('value');
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

  async userSelectLastOption(): Promise<void> {
    await this.userSelect.all(by.tagName('option')).last().click();
  }

  async userSelectOption(option: string): Promise<void> {
    await this.userSelect.sendKeys(option);
  }

  getUserSelect(): ElementFinder {
    return this.userSelect;
  }

  async getUserSelectedOption(): Promise<string> {
    return await this.userSelect.element(by.css('option:checked')).getText();
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

export class PostDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-post-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-post'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}

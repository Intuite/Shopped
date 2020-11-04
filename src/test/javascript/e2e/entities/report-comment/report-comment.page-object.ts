import { element, by, ElementFinder } from 'protractor';

export class ReportCommentComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-report-comment div table .btn-danger'));
  title = element.all(by.css('jhi-report-comment div h2#page-heading span')).first();
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

export class ReportCommentUpdatePage {
  pageTitle = element(by.id('jhi-report-comment-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  createdInput = element(by.id('field_created'));
  statusSelect = element(by.id('field_status'));

  typeSelect = element(by.id('field_type'));
  commentSelect = element(by.id('field_comment'));
  userSelect = element(by.id('field_user'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setCreatedInput(created: string): Promise<void> {
    await this.createdInput.sendKeys(created);
  }

  async getCreatedInput(): Promise<string> {
    return await this.createdInput.getAttribute('value');
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

  async typeSelectLastOption(): Promise<void> {
    await this.typeSelect.all(by.tagName('option')).last().click();
  }

  async typeSelectOption(option: string): Promise<void> {
    await this.typeSelect.sendKeys(option);
  }

  getTypeSelect(): ElementFinder {
    return this.typeSelect;
  }

  async getTypeSelectedOption(): Promise<string> {
    return await this.typeSelect.element(by.css('option:checked')).getText();
  }

  async commentSelectLastOption(): Promise<void> {
    await this.commentSelect.all(by.tagName('option')).last().click();
  }

  async commentSelectOption(option: string): Promise<void> {
    await this.commentSelect.sendKeys(option);
  }

  getCommentSelect(): ElementFinder {
    return this.commentSelect;
  }

  async getCommentSelectedOption(): Promise<string> {
    return await this.commentSelect.element(by.css('option:checked')).getText();
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

export class ReportCommentDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-reportComment-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-reportComment'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}

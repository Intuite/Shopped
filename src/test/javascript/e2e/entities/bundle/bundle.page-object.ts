import { element, by, ElementFinder } from 'protractor';

export class BundleComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-bundle div table .btn-danger'));
  title = element.all(by.css('jhi-bundle div h2#page-heading span')).first();
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

export class BundleUpdatePage {
  pageTitle = element(by.id('jhi-bundle-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  nameInput = element(by.id('field_name'));
  costInput = element(by.id('field_cost'));
  cookieAmountInput = element(by.id('field_cookieAmount'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNameInput(name: string): Promise<void> {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput(): Promise<string> {
    return await this.nameInput.getAttribute('value');
  }

  async setCostInput(cost: string): Promise<void> {
    await this.costInput.sendKeys(cost);
  }

  async getCostInput(): Promise<string> {
    return await this.costInput.getAttribute('value');
  }

  async setCookieAmountInput(cookieAmount: string): Promise<void> {
    await this.cookieAmountInput.sendKeys(cookieAmount);
  }

  async getCookieAmountInput(): Promise<string> {
    return await this.cookieAmountInput.getAttribute('value');
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

export class BundleDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-bundle-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-bundle'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
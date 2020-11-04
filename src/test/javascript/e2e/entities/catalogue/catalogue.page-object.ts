import { element, by, ElementFinder } from 'protractor';

export class CatalogueComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-catalogue div table .btn-danger'));
  title = element.all(by.css('jhi-catalogue div h2#page-heading span')).first();
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

export class CatalogueUpdatePage {
  pageTitle = element(by.id('jhi-catalogue-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  idCatalogueInput = element(by.id('field_idCatalogue'));
  valueInput = element(by.id('field_value'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setIdCatalogueInput(idCatalogue: string): Promise<void> {
    await this.idCatalogueInput.sendKeys(idCatalogue);
  }

  async getIdCatalogueInput(): Promise<string> {
    return await this.idCatalogueInput.getAttribute('value');
  }

  async setValueInput(value: string): Promise<void> {
    await this.valueInput.sendKeys(value);
  }

  async getValueInput(): Promise<string> {
    return await this.valueInput.getAttribute('value');
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

export class CatalogueDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-catalogue-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-catalogue'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}

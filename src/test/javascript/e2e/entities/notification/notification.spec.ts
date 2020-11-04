import { browser, ExpectedConditions as ec /* , protractor, promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  NotificationComponentsPage,
  /* NotificationDeleteDialog, */
  NotificationUpdatePage,
} from './notification.page-object';

const expect = chai.expect;

describe('Notification e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let notificationComponentsPage: NotificationComponentsPage;
  let notificationUpdatePage: NotificationUpdatePage;
  /* let notificationDeleteDialog: NotificationDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Notifications', async () => {
    await navBarPage.goToEntity('notification');
    notificationComponentsPage = new NotificationComponentsPage();
    await browser.wait(ec.visibilityOf(notificationComponentsPage.title), 5000);
    expect(await notificationComponentsPage.getTitle()).to.eq('shoppedApp.notification.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(notificationComponentsPage.entities), ec.visibilityOf(notificationComponentsPage.noResult)),
      1000
    );
  });

  it('should load create Notification page', async () => {
    await notificationComponentsPage.clickOnCreateButton();
    notificationUpdatePage = new NotificationUpdatePage();
    expect(await notificationUpdatePage.getPageTitle()).to.eq('shoppedApp.notification.home.createOrEditLabel');
    await notificationUpdatePage.cancel();
  });

  /* it('should create and save Notifications', async () => {
        const nbButtonsBeforeCreate = await notificationComponentsPage.countDeleteButtons();

        await notificationComponentsPage.clickOnCreateButton();

        await promise.all([
            notificationUpdatePage.setContentInput('content'),
            notificationUpdatePage.setCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            notificationUpdatePage.statusSelectLastOption(),
            notificationUpdatePage.typeSelectLastOption(),
            notificationUpdatePage.userSelectLastOption(),
        ]);

        expect(await notificationUpdatePage.getContentInput()).to.eq('content', 'Expected Content value to be equals to content');
        expect(await notificationUpdatePage.getCreatedInput()).to.contain('2001-01-01T02:30', 'Expected created value to be equals to 2000-12-31');

        await notificationUpdatePage.save();
        expect(await notificationUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await notificationComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /* it('should delete last Notification', async () => {
        const nbButtonsBeforeDelete = await notificationComponentsPage.countDeleteButtons();
        await notificationComponentsPage.clickOnLastDeleteButton();

        notificationDeleteDialog = new NotificationDeleteDialog();
        expect(await notificationDeleteDialog.getDialogTitle())
            .to.eq('shoppedApp.notification.delete.question');
        await notificationDeleteDialog.clickOnConfirmButton();

        expect(await notificationComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});

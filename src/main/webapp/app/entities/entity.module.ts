import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'user-profile',
        loadChildren: () => import('./user-profile/user-profile.module').then(m => m.ShoppedUserProfileModule),
      },
      {
        path: 'cookies',
        loadChildren: () => import('./cookies/cookies.module').then(m => m.ShoppedCookiesModule),
      },
      {
        path: 'follower',
        loadChildren: () => import('./follower/follower.module').then(m => m.ShoppedFollowerModule),
      },
      {
        path: 'transaction',
        loadChildren: () => import('./transaction/transaction.module').then(m => m.ShoppedTransactionModule),
      },
      {
        path: 'notification-type',
        loadChildren: () => import('./notification-type/notification-type.module').then(m => m.ShoppedNotificationTypeModule),
      },
      {
        path: 'notification',
        loadChildren: () => import('./notification/notification.module').then(m => m.ShoppedNotificationModule),
      },
      {
        path: 'commendation',
        loadChildren: () => import('./commendation/commendation.module').then(m => m.ShoppedCommendationModule),
      },
      {
        path: 'log-type',
        loadChildren: () => import('./log-type/log-type.module').then(m => m.ShoppedLogTypeModule),
      },
      {
        path: 'log',
        loadChildren: () => import('./log/log.module').then(m => m.ShoppedLogModule),
      },
      {
        path: 'cart',
        loadChildren: () => import('./cart/cart.module').then(m => m.ShoppedCartModule),
      },
      {
        path: 'cart-has-recipe',
        loadChildren: () => import('./cart-has-recipe/cart-has-recipe.module').then(m => m.ShoppedCartHasRecipeModule),
      },
      {
        path: 'comment',
        loadChildren: () => import('./comment/comment.module').then(m => m.ShoppedCommentModule),
      },
      {
        path: 'report-type',
        loadChildren: () => import('./report-type/report-type.module').then(m => m.ShoppedReportTypeModule),
      },
      {
        path: 'report-comment',
        loadChildren: () => import('./report-comment/report-comment.module').then(m => m.ShoppedReportCommentModule),
      },
      {
        path: 'report-post',
        loadChildren: () => import('./report-post/report-post.module').then(m => m.ShoppedReportPostModule),
      },
      {
        path: 'recipe',
        loadChildren: () => import('./recipe/recipe.module').then(m => m.ShoppedRecipeModule),
      },
      {
        path: 'recipe-shared',
        loadChildren: () => import('./recipe-shared/recipe-shared.module').then(m => m.ShoppedRecipeSharedModule),
      },
      {
        path: 'collection',
        loadChildren: () => import('./collection/collection.module').then(m => m.ShoppedCollectionModule),
      },
      {
        path: 'collection-has-recipe',
        loadChildren: () => import('./collection-has-recipe/collection-has-recipe.module').then(m => m.ShoppedCollectionHasRecipeModule),
      },
      {
        path: 'ingredient',
        loadChildren: () => import('./ingredient/ingredient.module').then(m => m.ShoppedIngredientModule),
      },
      {
        path: 'recipe-has-ingredient',
        loadChildren: () => import('./recipe-has-ingredient/recipe-has-ingredient.module').then(m => m.ShoppedRecipeHasIngredientModule),
      },
      {
        path: 'cart-has-ingredient',
        loadChildren: () => import('./cart-has-ingredient/cart-has-ingredient.module').then(m => m.ShoppedCartHasIngredientModule),
      },
      {
        path: 'tag-type',
        loadChildren: () => import('./tag-type/tag-type.module').then(m => m.ShoppedTagTypeModule),
      },
      {
        path: 'recipe-tag',
        loadChildren: () => import('./recipe-tag/recipe-tag.module').then(m => m.ShoppedRecipeTagModule),
      },
      {
        path: 'recipe-has-recipe-tag',
        loadChildren: () => import('./recipe-has-recipe-tag/recipe-has-recipe-tag.module').then(m => m.ShoppedRecipeHasRecipeTagModule),
      },
      {
        path: 'ingredient-tag',
        loadChildren: () => import('./ingredient-tag/ingredient-tag.module').then(m => m.ShoppedIngredientTagModule),
      },
      {
        path: 'ingredient-has-ingredient-tag',
        loadChildren: () =>
          import('./ingredient-has-ingredient-tag/ingredient-has-ingredient-tag.module').then(
            m => m.ShoppedIngredientHasIngredientTagModule
          ),
      },
      {
        path: 'award',
        loadChildren: () => import('./award/award.module').then(m => m.ShoppedAwardModule),
      },
      {
        path: 'post',
        loadChildren: () => import('./post/post.module').then(m => m.ShoppedPostModule),
      },
      {
        path: 'bite',
        loadChildren: () => import('./bite/bite.module').then(m => m.ShoppedBiteModule),
      },
      {
        path: 'message',
        loadChildren: () => import('./message/message.module').then(m => m.ShoppedMessageModule),
      },
      {
        path: 'catalogue',
        loadChildren: () => import('./catalogue/catalogue.module').then(m => m.ShoppedCatalogueModule),
      },
      {
        path: 'unit',
        loadChildren: () => import('./unit/unit.module').then(m => m.ShoppedUnitModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class ShoppedEntityModule {}

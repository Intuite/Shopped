import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';
import { IPost } from 'app/shared/model/post.model';
import { IRecipe } from 'app/shared/model/recipe.model';
import { RecipeService } from 'app/entities/recipe/recipe.service';
import { IRecipeHasRecipeTag } from 'app/shared/model/recipe-has-recipe-tag.model';
import { IngredientService } from 'app/entities/ingredient/ingredient.service';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/user/account.model';
import { Log } from 'app/shared/model/log.model';
import { LogService } from 'app/entities/log/log.service';
import * as moment from 'moment';
import { Bite } from 'app/shared/model/bite.model';
import { BiteService } from 'app/entities/bite/bite.service';
import { PostService } from 'app/entities/post/post.service';
import { NotificationService } from 'app/entities/notification/notification.service';
import { Notification } from 'app/shared/model/notification.model';

interface FullIngredient {
  id?: number;
  amount?: number;
  name?: string;
  imageContentType?: string;
  image?: any;
  unitAbbrev?: string;
}

@Component({
  selector: 'jhi-post-detail',
  templateUrl: './post-detail.component.html',
  styleUrls: ['./post-detail.component.scss'],
})
export class PostDetailComponent implements OnInit {
  post: IPost | null = null;
  recipe: IRecipe | null | undefined;
  eventSubscriber?: Subscription;
  recipeTags!: IRecipeHasRecipeTag[] | null;
  ingredients: FullIngredient[] = [];
  account?: Account | undefined;
  biteStatus = false;
  statusOptions = ['ACTIVE', 'INACTIVE'];
  countBite: any = 0;

  constructor(
    protected recipeService: RecipeService,
    protected ingredientService: IngredientService,
    protected dataUtils: JhiDataUtils,
    protected activatedRoute: ActivatedRoute,
    protected accountService: AccountService,
    protected postService: PostService,
    private logService: LogService,
    private biteService: BiteService,
    private notificationService: NotificationService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ post }) => (this.post = post));
    this.accountService.identity().subscribe(res => (this.account = res || undefined));
    this.recipeService.query().subscribe(
      () => this.onSuccess(),
      () => this.onError()
    );
    this.saveHistory();
    this.countBites();
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }

  onError(): void {
    console.warn('There was an error');
  }

  saveHistory(): void {
    const description = JSON.stringify({
      user: this.account?.login,
      postId: this.post?.id,
    });
    this.logService
      .create(new Log(undefined, description, moment().startOf('minute'), 'Post view', 3, this.account?.login, this.account?.id))
      .subscribe(
        () => console.warn('Post view log succesful'),
        () => console.warn('Post view log failed')
      );
  }

  protected onSuccess(): void {
    this.recipeService.find(this.post?.recipeId).subscribe(response => {
      this.recipe = response.body;
      if (this.post?.recipeId !== undefined) {
        this.recipeService.findRecipeHasIngredients(this.post.recipeId).subscribe(recipeHasIng => {
          if (recipeHasIng.body !== null) {
            recipeHasIng.body.forEach(ing => {
              if (ing.id !== undefined) {
                this.ingredientService.find(ing.id).subscribe(fullIng => {
                  this.ingredients.push({
                    id: ing.ingredientId,
                    name: ing.ingredientName,
                    amount: ing.amount,
                    unitAbbrev: fullIng.body!.unitAbbrev,
                    image: fullIng.body?.image,
                    imageContentType: fullIng.body?.imageContentType,
                  });
                });
              }
            });
          }
        });
        this.recipeService.findRecipeHasRecipeTags(this.post?.recipeId).subscribe(recipeTags => (this.recipeTags = recipeTags.body));
      }
    });
  }

  addBite(): void {
    this.biteService
      .create(new Bite(undefined, moment().startOf('minute'), this.post?.status, this.post?.id, this.account?.login, this.account?.id))
      .subscribe(
        () => (this.saveHistoryBite(), this.addNotificationBite(), this.countBites(), (this.biteStatus = true)),
        () => console.warn('bite failed')
      );
  }

  saveHistoryBite(): void {
    const description = JSON.stringify({
      user: this.account?.login,
      postId: this.post?.id,
    });
    this.logService
      .create(new Log(undefined, description, moment().startOf('minute'), 'Post bite', 4, this.account?.login, this.account?.id))
      .subscribe(
        () => console.warn('Post bite log succesful'),
        () => console.warn('Post bite log failed')
      );
  }

  addNotificationBite(): void {
    const content = JSON.stringify({
      user: this.account?.login,
      postRecipeName: this.post?.recipeName,
    });
    this.notificationService
      .create(
        new Notification(
          undefined,
          content,
          moment().startOf('minute'),
          this.post?.status,
          'Bite',
          1,
          this.account?.login,
          this.account?.id
        )
      )
      .subscribe(
        () => console.warn('Notification bite succesful'),
        () => console.warn('Notification bite failed')
      );
  }

  countBites(): void {
    this.postService.countBites(this.post?.id).subscribe(res => ((this.countBite = res), this.checkBiteStatus()));
  }

  checkBiteStatus(): void {
    let i = 0;
    let found = false;
    while (i < this.countBite.body.length && found === false) {
      if (this.countBite.body[i].userId === this.account?.id) {
        this.biteStatus = true;
        found = true;
      }
      i++;
    }
  }
}

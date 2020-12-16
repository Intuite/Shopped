import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { JhiEventManager } from 'ng-jhipster';

import { Bite, IBite } from 'app/shared/model/bite.model';
import { BiteService } from 'app/entities/bite/bite.service';
import { PostService } from 'app/entities/post/post.service';
import { RecipeService } from 'app/entities/recipe/recipe.service';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/user/account.model';
import { ILog } from 'app/shared/model/log.model';
import { Subscription } from 'rxjs';
import { Status } from 'app/shared/model/enumerations/status.model';

interface RecomendedItem {
  id?: number;
  status?: Status;
  postId?: number;
  recipeName?: string;
  recipeId?: number;
  imageContentType?: string;
  image?: any;
  biteCounter?: number;
}

@Component({
  selector: 'jhi-recomend',
  templateUrl: './recomend.component.html',
  styleUrls: ['./recomend.component.scss'],
})
export class RecomendComponent implements OnInit {
  account: Account | null = null;
  authSubscription?: Subscription;
  bites: IBite[] = [];
  recomendedItems: RecomendedItem[] = [];
  displayRecomendedItems: RecomendedItem[] = [];
  statusOptions = ['ACTIVE', 'INACTIVE', 'BLOCKED'];
  biteCounter: any[] = [];
  logViewPost: ILog[] = [];
  logPostId: any = [];

  constructor(
    protected biteService: BiteService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected postService: PostService,
    protected recipeService: RecipeService,
    private accountService: AccountService
  ) {}

  ngOnInit(): void {
    this.authSubscription = this.accountService.getAuthenticationState().subscribe(account => (this.account = account));

    this.postService.findUserLogs(this.account?.id).subscribe(logs => {
      this.logViewPost = logs.body;
    });

    this.biteService.query().subscribe(
      (res: HttpResponse<IBite[]>) => (
        this.countBitesDistribution(res.body), this.joinRecipe(), this.sortRecomendedItems(), this.cleanRecomended(), this.checkUserViews()
      ),
      () => this.onError()
    );
  }

  private onError(): void {
    console.warn('There was an error');
  }

  private onSuccessPost(body: IBite[] | null): void {
    this.bites = body || [];
  }

  joinRecipe(): void {
    if (this.biteCounter !== null) {
      this.biteCounter.forEach(bite =>
        this.postService.find(bite.postId).subscribe(post => {
          if (post.body !== null) {
            this.recipeService.find(post.body.recipeId).subscribe(recipe => {
              if (recipe.body !== null) {
                this.recomendedItems.push({
                  id: post.body?.id,
                  status: post.body?.status,
                  recipeName: post.body?.recipeName,
                  imageContentType: recipe.body.imageContentType,
                  image: recipe.body.image,
                  biteCounter: bite.biteCounter,
                });
              }
            });
          }
        })
      );
    }
  }

  countBitesDistribution(bites: IBite[] | null): void {
    if (bites !== null) {
      let prev: IBite = new Bite();

      bites?.sort((a: any, b: any) => {
        const x = a.postId,
          y = b.postId;
        return x === y ? 0 : x > y ? 1 : -1;
      });

      for (let i = 0; i < bites.length; i++) {
        if (bites[i].postId !== prev.postId) {
          if (bites[i] !== undefined) {
            this.biteCounter.push({
              postId: bites[i]?.postId,
              biteCounter: 1,
            });
          }
        } else {
          this.biteCounter[this.biteCounter.length - 1].biteCounter++;
        }
        prev = bites[i];
      }

      this.biteCounter.sort((a: any, b: any) => {
        const x = a.biteCounter,
          y = b.biteCounter;
        return x === y ? 0 : x < y ? 1 : -1;
      });
    }
  }

  sortRecomendedItems(): void {
    this.recomendedItems.sort((a: any, b: any) => {
      const x = a.biteCounter,
        y = b.biteCounter;
      return x === y ? 0 : x < y ? 1 : -1;
    });
  }

  cleanRecomended(): void {
    let i = 0;
    while (i < this.recomendedItems.length) {
      if (this.recomendedItems[i].status === this.statusOptions[1] || this.recomendedItems[i].status === this.statusOptions[2]) {
        this.recomendedItems.splice(i, 1);
      } else {
        i++;
      }
    }
  }

  checkUserViews(): void {
    // this.logPostId = JSON.parse(this.logViewPost[0].description!)

    this.displayRecomendedItems = this.recomendedItems;
  }
}

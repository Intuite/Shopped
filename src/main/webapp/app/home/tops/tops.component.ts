import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { JhiEventManager } from 'ng-jhipster';

import { IBite, Bite } from 'app/shared/model/bite.model';
import { BiteService } from 'app/entities/bite/bite.service';
import { Status } from 'app/shared/model/enumerations/status.model';
import { PostService } from 'app/entities/post/post.service';
import { RecipeService } from 'app/entities/recipe/recipe.service';

interface TopItem {
  id?: number;
  status?: Status;
  recipeName?: string;
  recipeId?: number;
  userLogin?: string;
  userId?: number;
  biteCounter?: number;
}

@Component({
  selector: 'jhi-tops',
  templateUrl: './tops.component.html',
  styleUrls: ['./tops.component.scss'],
})
export class TopsComponent implements OnInit {
  bites: IBite[] = [];
  topItems: TopItem[] = [];
  displayTopItems: TopItem[] = [];
  statusOptions = ['ACTIVE', 'INACTIVE', 'BLOCKED'];
  biteCounter: any[] = [];

  constructor(
    protected biteService: BiteService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected postService: PostService,
    protected recipeService: RecipeService
  ) {}

  ngOnInit(): void {
    this.biteService.query().subscribe(
      (res: HttpResponse<IBite[]>) => (this.countBitesDistribution(res.body), this.joinPost(), this.sortTopItems()),
      () => this.onError()
    );
  }

  private onError(): void {
    console.warn('There was an error');
  }

  private onSuccessPost(body: IBite[] | null): void {
    this.bites = body || [];
  }

  joinPost(): void {
    if (this.bites !== null) {
      this.biteCounter.forEach(item =>
        this.postService.find(item.postId).subscribe(post => {
          if (post.body !== null) {
            this.topItems.push({
              id: post.body.id,
              recipeName: post.body.recipeName,
              recipeId: post.body.recipeId,
              userLogin: post.body.userLogin,
              userId: post.body.userId,
              biteCounter: item.biteCounter,
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

  sortTopItems(): void {
    this.topItems.sort((a: any, b: any) => {
      const x = a.biteCounter,
        y = b.biteCounter;
      return x === y ? 0 : x < y ? 1 : -1;
    });

    this.displayTopItems = this.topItems;
  }
}

import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { JhiEventManager } from 'ng-jhipster';

import { Bite, IBite } from 'app/shared/model/bite.model';
import { BiteService } from 'app/entities/bite/bite.service';
import { PostService } from 'app/entities/post/post.service';
import { RecipeService } from 'app/entities/recipe/recipe.service';

interface RecomendedPost {
  id?: number;
  caption?: string;
  recipeName?: string;
  recipeId?: number;
  userLogin?: string;
  userId?: number;
  imageContentType?: string;
  image?: any;
}

@Component({
  selector: 'jhi-recomend',
  templateUrl: './recomend.component.html',
  styleUrls: ['./recomend.component.scss'],
})
export class RecomendComponent implements OnInit {
  bites: IBite[] = [];
  recomendedItems: RecomendedPost[] = [];
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
    this.biteService.queryAll().subscribe(
      (res: HttpResponse<IBite[]>) => (this.countBitesDistribution(res.body), this.joinRecipe()),
      () => this.onError()
    );
  }

  private onError(): void {
    console.warn('There was an error');
  }

  private onSuccessPost(body: IBite[] | null): void {
    this.bites = body || [];

    this.joinRecipe();
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

  joinRecipe(): void {
    if (this.biteCounter !== null) {
      this.biteCounter.forEach(bite =>
        this.postService.find(bite.postId).subscribe(post => {
          if (post.body !== null) {
            this.recipeService.find(post.body.recipeId).subscribe(recipe => {
              if (recipe.body !== null) {
                this.recomendedItems.push({
                  id: post.body?.id,
                  recipeName: post.body?.recipeName,
                  imageContentType: recipe.body.imageContentType,
                  image: recipe.body.image,
                });
              }
            });
          }
        })
      );
    }
  }
}

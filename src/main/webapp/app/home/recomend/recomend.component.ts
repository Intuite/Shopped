import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { JhiEventManager } from 'ng-jhipster';

import { IBite } from 'app/shared/model/bite.model';
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
      (res: HttpResponse<IBite[]>) => this.onSuccessPost(res.body),
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

  joinRecipe(): void {
    if (this.bites !== null) {
      this.bites.forEach(bite =>
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

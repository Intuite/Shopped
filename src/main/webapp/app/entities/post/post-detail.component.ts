import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';
import { IPost } from 'app/shared/model/post.model';
import { IRecipe, Recipe } from 'app/shared/model/recipe.model';
import { RecipeService } from 'app/entities/recipe/recipe.service';

@Component({
  selector: 'jhi-post-detail',
  templateUrl: './post-detail.component.html',
  styleUrls: ['./post-detail.component.scss'],
})
export class PostDetailComponent implements OnInit {
  post: IPost | null = null;
  recipe: IRecipe | null = null;
  eventSubscriber?: Subscription;

  constructor(protected recipeService: RecipeService, protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ post }) => (this.post = post));
    this.recipeService.find(1).subscribe(response => (this.recipe = response.body || null));
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

  protected onError(): void {
    console.warn('There was an error');
  }
}

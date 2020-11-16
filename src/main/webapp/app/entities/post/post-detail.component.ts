import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { HttpResponse } from '@angular/common/http';
import { JhiDataUtils } from 'ng-jhipster';
import { IPost } from 'app/shared/model/post.model';
import { Recipe, IRecipe } from 'app/shared/model/recipe.model';
import { RecipeService } from 'app/entities/recipe/recipe.service';

@Component({
  selector: 'jhi-post-detail',
  templateUrl: './post-detail.component.html',
})
export class PostDetailComponent implements OnInit {
  post: IPost | null = null;
  recipe?: Recipe[];
  eventSubscriber?: Subscription;

  constructor(protected recipeService: RecipeService, protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ post }) => (this.post = post));
    this.recipeService.find(3).subscribe(
      (res: HttpResponse<IRecipe[]>) => this.onSuccess(res.body),
      () => this.onError()
    );
  }

  protected onSuccess(data: Recipe[] | null): void {
    this.recipe = data || [];
  }

  protected onError(): void {
    console.warn('There was an error');
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
}

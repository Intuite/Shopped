import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, Subject } from 'rxjs';
import { map, tap } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IRecipe } from 'app/shared/model/recipe.model';
import { IRecipeHasRecipeTag } from 'app/shared/model/recipe-has-recipe-tag.model';
import { RecipeHasRecipeTagService } from 'app/entities/recipe-has-recipe-tag/recipe-has-recipe-tag.service';
import { RecipeHasIngredientService } from 'app/entities/recipe-has-ingredient/recipe-has-ingredient.service';
import { IRecipeHasIngredient } from 'app/shared/model/recipe-has-ingredient.model';

type EntityResponseType = HttpResponse<IRecipe>;
type EntityArrayResponseType = HttpResponse<IRecipe[]>;

@Injectable({ providedIn: 'root' })
export class RecipeService {
  public resourceUrl = SERVER_API_URL + 'api/recipes';

  refreshNeeded$ = new Subject<void>();

  constructor(
    protected http: HttpClient,
    protected recipeHasRecipeTagService: RecipeHasRecipeTagService,
    protected recipeHasIngredientService: RecipeHasIngredientService
  ) {}

  getRefreshNeed(): any {
    return this.refreshNeeded$;
  }

  findRecipeHasRecipeTags(id: number): Observable<HttpResponse<IRecipeHasRecipeTag[]>> {
    return this.recipeHasRecipeTagService.query({
      ...{ 'recipeId.equals': id },
    });
  }

  findRecipeHasIngredients(id: number): Observable<HttpResponse<IRecipeHasIngredient[]>> {
    return this.recipeHasIngredientService.query({
      ...{ 'recipeId.equals': id },
    });
  }

  create(recipe: IRecipe): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(recipe);
    return this.http
      .post<IRecipe>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(
        map((res: EntityResponseType) => this.convertDateFromServer(res)),
        tap(() => {
          this.refreshNeeded$.next();
        })
      );
  }

  update(recipe: IRecipe): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(recipe);
    return this.http
      .put<IRecipe>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(
        map((res: EntityResponseType) => this.convertDateFromServer(res)),
        tap(() => {
          this.refreshNeeded$.next();
        })
      );
  }

  find(id: number | undefined): Observable<EntityResponseType> {
    return this.http
      .get<IRecipe>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IRecipe[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  queryAll(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRecipe[]>(`${this.resourceUrl}/all`, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(recipe: IRecipe): IRecipe {
    const copy: IRecipe = Object.assign({}, recipe, {
      creation: recipe.creation && recipe.creation.isValid() ? recipe.creation.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.creation = res.body.creation ? moment(res.body.creation) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((recipe: IRecipe) => {
        recipe.creation = recipe.creation ? moment(recipe.creation) : undefined;
      });
    }
    return res;
  }
}

import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IIngredientTag } from 'app/shared/model/ingredient-tag.model';
import { IRecipeTag } from 'app/shared/model/recipe-tag.model';

type EntityResponseType = HttpResponse<IIngredientTag>;
type EntityArrayResponseType = HttpResponse<IIngredientTag[]>;

@Injectable({ providedIn: 'root' })
export class IngredientTagService {
  public resourceUrl = SERVER_API_URL + 'api/ingredient-tags';

  constructor(protected http: HttpClient) {}

  create(ingredientTag: IIngredientTag): Observable<EntityResponseType> {
    return this.http.post<IIngredientTag>(this.resourceUrl, ingredientTag, { observe: 'response' });
  }

  update(ingredientTag: IIngredientTag): Observable<EntityResponseType> {
    return this.http.put<IIngredientTag>(this.resourceUrl, ingredientTag, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IIngredientTag>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IIngredientTag[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  queryAll(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRecipeTag[]>(`${this.resourceUrl}/all`, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}

import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IIngredientHasIngredientTag } from 'app/shared/model/ingredient-has-ingredient-tag.model';

type EntityResponseType = HttpResponse<IIngredientHasIngredientTag>;
type EntityArrayResponseType = HttpResponse<IIngredientHasIngredientTag[]>;

@Injectable({ providedIn: 'root' })
export class IngredientHasIngredientTagService {
  public resourceUrl = SERVER_API_URL + 'api/ingredient-has-ingredient-tags';

  constructor(protected http: HttpClient) {}

  create(ingredientHasIngredientTag: IIngredientHasIngredientTag): Observable<EntityResponseType> {
    return this.http.post<IIngredientHasIngredientTag>(this.resourceUrl, ingredientHasIngredientTag, { observe: 'response' });
  }

  update(ingredientHasIngredientTag: IIngredientHasIngredientTag): Observable<EntityResponseType> {
    return this.http.put<IIngredientHasIngredientTag>(this.resourceUrl, ingredientHasIngredientTag, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IIngredientHasIngredientTag>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IIngredientHasIngredientTag[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}

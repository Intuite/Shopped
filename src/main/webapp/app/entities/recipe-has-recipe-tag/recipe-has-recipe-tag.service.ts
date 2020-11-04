import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IRecipeHasRecipeTag } from 'app/shared/model/recipe-has-recipe-tag.model';

type EntityResponseType = HttpResponse<IRecipeHasRecipeTag>;
type EntityArrayResponseType = HttpResponse<IRecipeHasRecipeTag[]>;

@Injectable({ providedIn: 'root' })
export class RecipeHasRecipeTagService {
  public resourceUrl = SERVER_API_URL + 'api/recipe-has-recipe-tags';

  constructor(protected http: HttpClient) {}

  create(recipeHasRecipeTag: IRecipeHasRecipeTag): Observable<EntityResponseType> {
    return this.http.post<IRecipeHasRecipeTag>(this.resourceUrl, recipeHasRecipeTag, { observe: 'response' });
  }

  update(recipeHasRecipeTag: IRecipeHasRecipeTag): Observable<EntityResponseType> {
    return this.http.put<IRecipeHasRecipeTag>(this.resourceUrl, recipeHasRecipeTag, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRecipeHasRecipeTag>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRecipeHasRecipeTag[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}

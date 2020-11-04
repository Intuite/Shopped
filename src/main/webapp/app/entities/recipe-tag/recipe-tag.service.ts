import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IRecipeTag } from 'app/shared/model/recipe-tag.model';

type EntityResponseType = HttpResponse<IRecipeTag>;
type EntityArrayResponseType = HttpResponse<IRecipeTag[]>;

@Injectable({ providedIn: 'root' })
export class RecipeTagService {
  public resourceUrl = SERVER_API_URL + 'api/recipe-tags';

  constructor(protected http: HttpClient) {}

  create(recipeTag: IRecipeTag): Observable<EntityResponseType> {
    return this.http.post<IRecipeTag>(this.resourceUrl, recipeTag, { observe: 'response' });
  }

  update(recipeTag: IRecipeTag): Observable<EntityResponseType> {
    return this.http.put<IRecipeTag>(this.resourceUrl, recipeTag, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRecipeTag>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRecipeTag[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}

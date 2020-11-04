import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IRecipeShared } from 'app/shared/model/recipe-shared.model';

type EntityResponseType = HttpResponse<IRecipeShared>;
type EntityArrayResponseType = HttpResponse<IRecipeShared[]>;

@Injectable({ providedIn: 'root' })
export class RecipeSharedService {
  public resourceUrl = SERVER_API_URL + 'api/recipe-shareds';

  constructor(protected http: HttpClient) {}

  create(recipeShared: IRecipeShared): Observable<EntityResponseType> {
    return this.http.post<IRecipeShared>(this.resourceUrl, recipeShared, { observe: 'response' });
  }

  update(recipeShared: IRecipeShared): Observable<EntityResponseType> {
    return this.http.put<IRecipeShared>(this.resourceUrl, recipeShared, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRecipeShared>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRecipeShared[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}

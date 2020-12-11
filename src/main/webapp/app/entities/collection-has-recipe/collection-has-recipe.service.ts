import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICollectionHasRecipe } from 'app/shared/model/collection-has-recipe.model';

type EntityResponseType = HttpResponse<ICollectionHasRecipe>;
type EntityArrayResponseType = HttpResponse<ICollectionHasRecipe[]>;

@Injectable({ providedIn: 'root' })
export class CollectionHasRecipeService {
  public resourceUrl = SERVER_API_URL + 'api/collection-has-recipes';

  constructor(protected http: HttpClient) {}

  create(collectionHasRecipe: ICollectionHasRecipe): Observable<EntityResponseType> {
    return this.http.post<ICollectionHasRecipe>(this.resourceUrl, collectionHasRecipe, { observe: 'response' });
  }

  update(collectionHasRecipe: ICollectionHasRecipe): Observable<EntityResponseType> {
    return this.http.put<ICollectionHasRecipe>(this.resourceUrl, collectionHasRecipe, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICollectionHasRecipe>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICollectionHasRecipe[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  queryAll(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICollectionHasRecipe[]>(`${this.resourceUrl}/all`, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}

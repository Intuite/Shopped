import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IRecipeHasIngredient } from 'app/shared/model/recipe-has-ingredient.model';
import { LogService } from 'app/entities/log/log.service';
import { Log } from 'app/shared/model/log.model';
import moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { AccountService } from 'app/core/auth/account.service';

type EntityResponseType = HttpResponse<IRecipeHasIngredient>;
type EntityArrayResponseType = HttpResponse<IRecipeHasIngredient[]>;

@Injectable({ providedIn: 'root' })
export class RecipeHasIngredientService {
  public resourceUrl = SERVER_API_URL + 'api/recipe-has-ingredients';

  constructor(protected http: HttpClient, private logService: LogService, private accountService: AccountService) {}

  create(recipeHasIngredient: IRecipeHasIngredient): Observable<EntityResponseType> {
    this.accountService.identity().subscribe(response => {
      if (response) {
        console.warn('creating log');
        const log = new Log(
          undefined,
          JSON.stringify({
            ingredientName: recipeHasIngredient.ingredientName,
            status: recipeHasIngredient.status,
          }),
          new Date() ? moment(new Date(), DATE_TIME_FORMAT) : undefined,
          'Recipe has Ingredient',
          7,
          response.login,
          response.id
        );
        this.logService.create(log).subscribe();
      }
    });
    return this.http.post<IRecipeHasIngredient>(this.resourceUrl, recipeHasIngredient, { observe: 'response' });
  }

  update(recipeHasIngredient: IRecipeHasIngredient): Observable<EntityResponseType> {
    return this.http.put<IRecipeHasIngredient>(this.resourceUrl, recipeHasIngredient, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRecipeHasIngredient>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRecipeHasIngredient[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}

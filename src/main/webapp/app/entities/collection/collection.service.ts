import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICollection } from 'app/shared/model/collection.model';

type EntityResponseType = HttpResponse<ICollection>;
type EntityArrayResponseType = HttpResponse<ICollection[]>;

@Injectable({ providedIn: 'root' })
export class CollectionService {
  public resourceUrl = SERVER_API_URL + 'api/collections';

  constructor(protected http: HttpClient) {}

  create(collection: ICollection): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(collection);
    return this.http
      .post<ICollection>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(collection: ICollection): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(collection);
    return this.http
      .put<ICollection>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICollection>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICollection[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  queryAll(req?: any): Observable<HttpResponse<ICollection[]>> {
    const options = createRequestOption(req);
    return this.http.get<ICollection[]>(`${this.resourceUrl}/all`, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(collection: ICollection): ICollection {
    const copy: ICollection = Object.assign({}, collection, {
      created: collection.created && collection.created.isValid() ? collection.created.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.created = res.body.created ? moment(res.body.created) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((collection: ICollection) => {
        collection.created = collection.created ? moment(collection.created) : undefined;
      });
    }
    return res;
  }
}

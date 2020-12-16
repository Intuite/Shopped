import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IBite } from 'app/shared/model/bite.model';

type EntityResponseType = HttpResponse<IBite>;
type EntityArrayResponseType = HttpResponse<IBite[]>;

@Injectable({ providedIn: 'root' })
export class BiteService {
  public resourceUrl = SERVER_API_URL + 'api/bites';

  constructor(protected http: HttpClient) {}

  create(bite: IBite): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(bite);
    return this.http
      .post<IBite>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(bite: IBite): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(bite);
    return this.http
      .put<IBite>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IBite>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IBite[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  queryAll(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBite[]>(`${this.resourceUrl}/all`, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(bite: IBite): IBite {
    const copy: IBite = Object.assign({}, bite, {
      created: bite.created && bite.created.isValid() ? bite.created.toJSON() : undefined,
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
      res.body.forEach((bite: IBite) => {
        bite.created = bite.created ? moment(bite.created) : undefined;
      });
    }
    return res;
  }
}

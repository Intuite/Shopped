import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICommendation } from 'app/shared/model/commendation.model';

type EntityResponseType = HttpResponse<ICommendation>;
type EntityArrayResponseType = HttpResponse<ICommendation[]>;

@Injectable({ providedIn: 'root' })
export class CommendationService {
  public resourceUrl = SERVER_API_URL + 'api/commendations';

  constructor(protected http: HttpClient) {}

  create(commendation: ICommendation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(commendation);
    return this.http
      .post<ICommendation>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(commendation: ICommendation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(commendation);
    return this.http
      .put<ICommendation>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICommendation>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICommendation[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(commendation: ICommendation): ICommendation {
    const copy: ICommendation = Object.assign({}, commendation, {
      date: commendation.date && commendation.date.isValid() ? commendation.date.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.date = res.body.date ? moment(res.body.date) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((commendation: ICommendation) => {
        commendation.date = commendation.date ? moment(commendation.date) : undefined;
      });
    }
    return res;
  }
}

import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ILog } from 'app/shared/model/log.model';

type EntityResponseType = HttpResponse<ILog>;
type EntityArrayResponseType = HttpResponse<ILog[]>;

@Injectable({ providedIn: 'root' })
export class LogService {
  public resourceUrl = SERVER_API_URL + 'api/logs';

  constructor(protected http: HttpClient) {}

  create(log: ILog): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(log);
    return this.http
      .post<ILog>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(log: ILog): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(log);
    return this.http
      .put<ILog>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ILog>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ILog[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(log: ILog): ILog {
    const copy: ILog = Object.assign({}, log, {
      created: log.created && log.created.isValid() ? log.created.toJSON() : undefined,
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
      res.body.forEach((log: ILog) => {
        log.created = log.created ? moment(log.created) : undefined;
      });
    }
    return res;
  }
}

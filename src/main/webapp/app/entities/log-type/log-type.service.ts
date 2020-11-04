import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ILogType } from 'app/shared/model/log-type.model';

type EntityResponseType = HttpResponse<ILogType>;
type EntityArrayResponseType = HttpResponse<ILogType[]>;

@Injectable({ providedIn: 'root' })
export class LogTypeService {
  public resourceUrl = SERVER_API_URL + 'api/log-types';

  constructor(protected http: HttpClient) {}

  create(logType: ILogType): Observable<EntityResponseType> {
    return this.http.post<ILogType>(this.resourceUrl, logType, { observe: 'response' });
  }

  update(logType: ILogType): Observable<EntityResponseType> {
    return this.http.put<ILogType>(this.resourceUrl, logType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ILogType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILogType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}

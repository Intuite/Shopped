import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICookies } from 'app/shared/model/cookies.model';

type EntityResponseType = HttpResponse<ICookies>;
type EntityArrayResponseType = HttpResponse<ICookies[]>;

@Injectable({ providedIn: 'root' })
export class CookiesService {
  public resourceUrl = SERVER_API_URL + 'api/cookies';

  constructor(protected http: HttpClient) {}

  create(cookies: ICookies): Observable<EntityResponseType> {
    return this.http.post<ICookies>(this.resourceUrl, cookies, { observe: 'response' });
  }

  update(cookies: ICookies): Observable<EntityResponseType> {
    return this.http.put<ICookies>(this.resourceUrl, cookies, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICookies>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICookies[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}

import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IBundle } from 'app/shared/model/bundle.model';

type EntityResponseType = HttpResponse<IBundle>;
type EntityArrayResponseType = HttpResponse<IBundle[]>;

@Injectable({ providedIn: 'root' })
export class BundleService {
  public resourceUrl = SERVER_API_URL + 'api/bundles';

  constructor(protected http: HttpClient) {}

  create(bundle: IBundle): Observable<EntityResponseType> {
    return this.http.post<IBundle>(this.resourceUrl, bundle, { observe: 'response' });
  }

  update(bundle: IBundle): Observable<EntityResponseType> {
    return this.http.put<IBundle>(this.resourceUrl, bundle, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBundle>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBundle[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}

import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAward } from 'app/shared/model/award.model';

type EntityResponseType = HttpResponse<IAward>;
type EntityArrayResponseType = HttpResponse<IAward[]>;

@Injectable({ providedIn: 'root' })
export class AwardService {
  public resourceUrl = SERVER_API_URL + 'api/awards';

  constructor(protected http: HttpClient) {}

  create(award: IAward): Observable<EntityResponseType> {
    return this.http.post<IAward>(this.resourceUrl, award, { observe: 'response' });
  }

  update(award: IAward): Observable<EntityResponseType> {
    return this.http.put<IAward>(this.resourceUrl, award, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAward>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAward[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  queryAll(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAward[]>(`${this.resourceUrl}/all`, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}

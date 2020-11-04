import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFollower } from 'app/shared/model/follower.model';

type EntityResponseType = HttpResponse<IFollower>;
type EntityArrayResponseType = HttpResponse<IFollower[]>;

@Injectable({ providedIn: 'root' })
export class FollowerService {
  public resourceUrl = SERVER_API_URL + 'api/followers';

  constructor(protected http: HttpClient) {}

  create(follower: IFollower): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(follower);
    return this.http
      .post<IFollower>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(follower: IFollower): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(follower);
    return this.http
      .put<IFollower>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IFollower>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFollower[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(follower: IFollower): IFollower {
    const copy: IFollower = Object.assign({}, follower, {
      created: follower.created && follower.created.isValid() ? follower.created.toJSON() : undefined,
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
      res.body.forEach((follower: IFollower) => {
        follower.created = follower.created ? moment(follower.created) : undefined;
      });
    }
    return res;
  }
}

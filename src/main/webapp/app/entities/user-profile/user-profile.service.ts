import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IUserProfile } from 'app/shared/model/user-profile.model';

type EntityResponseType = HttpResponse<IUserProfile>;
type EntityArrayResponseType = HttpResponse<IUserProfile[]>;

@Injectable({ providedIn: 'root' })
export class UserProfileService {
  public resourceUrl = SERVER_API_URL + 'api/user-profiles';

  constructor(protected http: HttpClient) {}

  create(userProfile: IUserProfile): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(userProfile);
    return this.http
      .post<IUserProfile>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(userProfile: IUserProfile): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(userProfile);
    return this.http
      .put<IUserProfile>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IUserProfile>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  findByUser(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IUserProfile>(`${this.resourceUrl}/user/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IUserProfile[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(userProfile: IUserProfile): IUserProfile {
    const copy: IUserProfile = Object.assign({}, userProfile, {
      birthDate: userProfile.birthDate && userProfile.birthDate.isValid() ? userProfile.birthDate.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.birthDate = res.body.birthDate ? moment(res.body.birthDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((userProfile: IUserProfile) => {
        userProfile.birthDate = userProfile.birthDate ? moment(userProfile.birthDate) : undefined;
      });
    }
    return res;
  }
}

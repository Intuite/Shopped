import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { INotificationType } from 'app/shared/model/notification-type.model';

type EntityResponseType = HttpResponse<INotificationType>;
type EntityArrayResponseType = HttpResponse<INotificationType[]>;

@Injectable({ providedIn: 'root' })
export class NotificationTypeService {
  public resourceUrl = SERVER_API_URL + 'api/notification-types';

  constructor(protected http: HttpClient) {}

  create(notificationType: INotificationType): Observable<EntityResponseType> {
    return this.http.post<INotificationType>(this.resourceUrl, notificationType, { observe: 'response' });
  }

  update(notificationType: INotificationType): Observable<EntityResponseType> {
    return this.http.put<INotificationType>(this.resourceUrl, notificationType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<INotificationType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<INotificationType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}

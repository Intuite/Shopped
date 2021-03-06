import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IReportType } from 'app/shared/model/report-type.model';

type EntityResponseType = HttpResponse<IReportType>;
type EntityArrayResponseType = HttpResponse<IReportType[]>;

@Injectable({ providedIn: 'root' })
export class ReportTypeService {
  public resourceUrl = SERVER_API_URL + 'api/report-types';

  constructor(protected http: HttpClient) {}

  create(reportType: IReportType): Observable<EntityResponseType> {
    return this.http.post<IReportType>(this.resourceUrl, reportType, { observe: 'response' });
  }

  update(reportType: IReportType): Observable<EntityResponseType> {
    return this.http.put<IReportType>(this.resourceUrl, reportType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IReportType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IReportType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}

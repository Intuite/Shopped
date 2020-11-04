import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IReportComment } from 'app/shared/model/report-comment.model';

type EntityResponseType = HttpResponse<IReportComment>;
type EntityArrayResponseType = HttpResponse<IReportComment[]>;

@Injectable({ providedIn: 'root' })
export class ReportCommentService {
  public resourceUrl = SERVER_API_URL + 'api/report-comments';

  constructor(protected http: HttpClient) {}

  create(reportComment: IReportComment): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(reportComment);
    return this.http
      .post<IReportComment>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(reportComment: IReportComment): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(reportComment);
    return this.http
      .put<IReportComment>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IReportComment>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IReportComment[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(reportComment: IReportComment): IReportComment {
    const copy: IReportComment = Object.assign({}, reportComment, {
      created: reportComment.created && reportComment.created.isValid() ? reportComment.created.toJSON() : undefined,
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
      res.body.forEach((reportComment: IReportComment) => {
        reportComment.created = reportComment.created ? moment(reportComment.created) : undefined;
      });
    }
    return res;
  }
}

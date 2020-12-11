import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, Subject } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPost } from 'app/shared/model/post.model';
import { map, tap } from 'rxjs/operators';
import { BiteService } from 'app/entities/bite/bite.service';
import { FollowerService } from 'app/entities/follower/follower.service';
import { CommentService } from 'app/entities/comment/comment.service';
import { RecipeService } from 'app/entities/recipe/recipe.service';
import * as moment from 'moment';

type EntityResponseType = HttpResponse<IPost>;
type EntityArrayResponseType = HttpResponse<IPost[]>;

@Injectable({ providedIn: 'root' })
export class PostService {
  public resourceUrl = SERVER_API_URL + 'api/posts';

  refreshNeeded$ = new Subject<void>();

  constructor(
    protected http: HttpClient,
    protected biteService: BiteService,
    protected followerService: FollowerService,
    protected commentService: CommentService,
    protected recipeService: RecipeService
  ) {}

  getRefreshNeed(): any {
    return this.refreshNeeded$;
  }

  countBites(id: number | undefined): Observable<HttpResponse<any>> {
    return this.biteService.query({
      ...{ 'postId.equals': id },
    });
  }

  countFollowers(id: number | undefined): Observable<HttpResponse<any>> {
    return this.followerService.query({
      ...{ 'userFollowedId.equals': id },
    });
  }

  findComments(id: number | undefined): Observable<HttpResponse<any>> {
    return this.commentService.query({
      ...{ 'postId.equals': id },
    });
  }

  findRecipe(id: number | undefined): Observable<HttpResponse<any>> {
    return this.recipeService.query({
      ...{ 'recipeId.equals': id },
    });
  }

  create(post: IPost): Observable<EntityResponseType> {
    return this.http
      .post<IPost>(this.resourceUrl, post, { observe: 'response' })
      .pipe(
        tap(() => {
          this.refreshNeeded$.next();
        })
      );
  }

  update(post: IPost): Observable<EntityResponseType> {
    return this.http
      .put<IPost>(this.resourceUrl, post, { observe: 'response' })
      .pipe(
        tap(() => {
          this.refreshNeeded$.next();
        })
      );
  }

  find(id: number | undefined): Observable<EntityResponseType> {
    return this.http
      .get<IPost>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPost[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  queryAll(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPost[]>(`${this.resourceUrl}/all`, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.date = res.body.date ? moment(res.body.date) : undefined;
    }
    return res;
  }
}

import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, ParamMap, Router, Data } from '@angular/router';
import { Subscription, combineLatest } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPost } from 'app/shared/model/post.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { PostService } from './post.service';
import { PostDeleteDialogComponent } from './post-delete-dialog.component';
import { Status } from 'app/shared/model/enumerations/status.model';
import { PostTableComponent } from 'app/shared/tables/post-table/post-table.component';
import { IRecipeTag } from 'app/shared/model/recipe-tag.model';

@Component({
  selector: 'jhi-post',
  templateUrl: './post.component.html',
})
export class PostComponent implements OnInit, OnDestroy {
  posts?: IPost[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;
  tableLoaded = false;
  requesting = false;
  @ViewChild('table', { static: false }) table!: PostTableComponent;

  @ViewChild('tbl', { static: false }) tbl!: PostTableComponent;

  constructor(
    protected postService: PostService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  ngOnInit(): void {
    this.handleNavigation();
    this.registerChangeInPosts();
  }

  ngOnDestroy(): void {
    // if (this.eventSubscriber) {
    //   this.eventManager.destroy(this.eventSubscriber);
    // }
  }

  trackId(index: number, item: IPost): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPosts(): void {
    // this.eventSubscriber = this.eventManager.subscribe('postListModification', () => this.loadPage());
  }

  delete(post: IPost): void {
    const modalRef = this.modalService.open(PostDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.post = post;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  changePage(pageIndex: number): void {
    const page = pageIndex;
    if (page !== this.page) {
      this.page = page;
    }
  }

  changePageSize(pageSize: number): void {
    if (pageSize !== this.itemsPerPage) {
      this.itemsPerPage = pageSize;
    }
  }

  navigate(): void {
    this.router.navigate(['/post'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc'),
      },
    });
  }

  setStatus(element: IPost, newStatus: boolean): void {
    this.postService
      .update({
        ...element,
        status: !newStatus ? (Status.ACTIVE.toUpperCase() as Status) : (Status.INACTIVE.toUpperCase() as Status),
      })
      .subscribe(() => {
        this.loadPage(this.page);
      });
  }

  protected handleNavigation(): void {
    combineLatest(this.activatedRoute.data, this.activatedRoute.queryParamMap, (data: Data, params: ParamMap) => {
      const page = params.get('page');
      const pageNumber = page !== null ? +page : 0;
      const pageSize = params.get('size');
      this.itemsPerPage = pageSize !== null ? +pageSize : ITEMS_PER_PAGE;
      const sort = (params.get('sort') ?? data['defaultSort']).split(',');
      const predicate = sort[0];
      const ascending = sort[1] === 'asc';
      if (pageNumber !== this.page || predicate !== this.predicate || ascending !== this.ascending) {
        this.predicate = predicate;
        this.ascending = ascending;
        this.loadPage(pageNumber);
      }
    }).subscribe();
  }

  protected onSuccess(data: IRecipeTag[] | null, headers: HttpHeaders, page: number): void {
    this.page = page;
    this.posts = data || [];
    this.totalItems = this.posts?.length ?? 0;
    this.ngbPaginationPage = this.page;
    this.requesting = false;
    if (this.tableLoaded) {
      this.refresh();
    }
    this.tableLoaded = true;
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page ?? 1;
    this.requesting = false;
  }

  protected refresh(): void {
    this.table.reloadSource(this.posts as IPost[]);
    this.navigate();
  }

  private loadPage(page?: number): void {
    const pageToLoad: number = page || this.page || 0;
    this.requesting = true;
    this.postService
      .queryAll({
        sort: this.sort(),
      })
      .subscribe(
        (res: HttpResponse<IPost[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
        () => this.onError()
      );
  }
}

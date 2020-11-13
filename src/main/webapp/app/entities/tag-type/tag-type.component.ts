import { Component, OnDestroy, OnInit } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data, ParamMap, Router } from '@angular/router';
import { combineLatest, Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITagType } from 'app/shared/model/tag-type.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { TagTypeService } from './tag-type.service';
import { TagTypeDeleteDialogComponent } from './tag-type-delete-dialog.component';

@Component({
  selector: 'jhi-tag-type',
  templateUrl: './tag-type.component.html',
})
export class TagTypeComponent implements OnInit, OnDestroy {
  tagTypes?: ITagType[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;
  tableLoaded = false;

  constructor(
    protected tagTypeService: TagTypeService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  ngOnInit(): void {
    this.handleNavigation();
    this.registerChangeInTagTypes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITagType): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTagTypes(): void {
    this.eventSubscriber = this.eventManager.subscribe('tagTypeListModification', () => this.loadPage());
  }

  delete(tagType: ITagType): void {
    const modalRef = this.modalService.open(TagTypeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.tagType = tagType;
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
    this.router.navigate(['/tag-type'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc'),
      },
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

  protected onSuccess(data: ITagType[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.tagTypes = data || [];
    this.ngbPaginationPage = this.page;
    this.tableLoaded = true;
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page ?? 1;
  }

  protected refresh(page: number): void {
    this.page = page;
  }

  private loadPage(page?: number): void {
    const pageToLoad: number = page || this.page || 0;
    if (this.totalItems === 0) {
      this.tagTypeService
        .queryAll({
          // page: pageToLoad - 1,
          // size: 439,
          sort: this.sort(),
        })
        .subscribe(
          (res: HttpResponse<ITagType[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
          () => this.onError()
        );
    } else {
      this.refresh(pageToLoad);
    }
  }
}

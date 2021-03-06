import { Component, OnInit, OnDestroy, ViewChild } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, ParamMap, Router, Data } from '@angular/router';
import { Subscription, combineLatest } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAward } from 'app/shared/model/award.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { AwardService } from './award.service';
import { AwardDeleteDialogComponent } from './award-delete-dialog.component';
import { Status } from 'app/shared/model/enumerations/status.model';
import { AwardTableComponent } from 'app/shared/tables/award-table/award-table.component';
@Component({
  selector: 'jhi-award',
  templateUrl: './award.component.html',
})
export class AwardComponent implements OnInit, OnDestroy {
  awards?: IAward[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;
  tableLoaded = false;
  requesting = false;
  @ViewChild('table', { static: false }) table!: AwardTableComponent;

  constructor(
    protected awardService: AwardService,
    protected activatedRoute: ActivatedRoute,
    protected dataUtils: JhiDataUtils,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page || 0;
    this.requesting = true;
    this.awardService
      .queryAll({
        sort: this.sort(),
      })
      .subscribe(
        (res: HttpResponse<IAward[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
        () => this.onError()
      );
  }

  ngOnInit(): void {
    this.handleNavigation();
    this.registerChangeInAwards();
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

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAward): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInAwards(): void {
    this.eventSubscriber = this.eventManager.subscribe('awardListModification', () => this.loadPage());
  }

  delete(award: IAward): void {
    const modalRef = this.modalService.open(AwardDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.award = award;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IAward[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = this.awards?.length ?? 0;
    this.page = page;
    this.awards = data || [];
    this.ngbPaginationPage = this.page;
    this.requesting = false;
    if (this.tableLoaded) {
      this.refresh();
    }
    this.tableLoaded = true;
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page ?? 1;
  }

  protected refresh(): void {
    this.table.reloadSource(this.awards as IAward[]);
    this.navigate();
  }

  navigate(): void {
    this.router.navigate(['/award'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc'),
      },
    });
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

  setStatus(element: IAward, newStatus: boolean): void {
    this.awardService
      .update({
        ...element,
        status: !newStatus ? (Status.ACTIVE.toUpperCase() as Status) : (Status.INACTIVE.toUpperCase() as Status),
      })
      .subscribe(() => {
        this.loadPage(this.page);
      });
  }
}

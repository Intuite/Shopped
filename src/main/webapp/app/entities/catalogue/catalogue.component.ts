import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, ParamMap, Router, Data } from '@angular/router';
import { Subscription, combineLatest } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICatalogue } from 'app/shared/model/catalogue.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { CatalogueService } from './catalogue.service';
import { CatalogueDeleteDialogComponent } from './catalogue-delete-dialog.component';

@Component({
  selector: 'jhi-catalogue',
  templateUrl: './catalogue.component.html',
})
export class CatalogueComponent implements OnInit, OnDestroy {
  catalogues?: ICatalogue[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;
  tableLoaded: any;
  requesting = false;

  constructor(
    protected catalogueService: CatalogueService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page || 1;
    this.requesting = true;
    if (this.totalItems === 0) {
      this.catalogueService
        .queryAll({
          sort: this.sort(),
        })
        .subscribe(
          (res: HttpResponse<ICatalogue[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
          () => this.onError()
        );
    } else {
      this.refresh(pageToLoad);
    }
  }

  ngOnInit(): void {
    this.handleNavigation();
    this.registerChangeInCatalogues();
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

  trackId(index: number, item: ICatalogue): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCatalogues(): void {
    this.eventSubscriber = this.eventManager.subscribe('catalogueListModification', () => this.loadPage());
  }

  delete(catalogue: ICatalogue): void {
    const modalRef = this.modalService.open(CatalogueDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.catalogue = catalogue;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: ICatalogue[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.catalogues = data || [];
    this.requesting = false;
    this.ngbPaginationPage = this.page;
    this.tableLoaded = true;
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page ?? 1;
    this.requesting = false;
  }

  protected refresh(page: number): void {
    this.page = page;
  }

  navigate(): void {
    this.router.navigate(['/catalogue'], {
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
}

import { Component, OnInit, OnDestroy, ViewChild } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, ParamMap, Router, Data } from '@angular/router';
import { Subscription, combineLatest } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IIngredient } from 'app/shared/model/ingredient.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { IngredientService } from './ingredient.service';
import { IngredientDeleteDialogComponent } from './ingredient-delete-dialog.component';
import { IngredientTableComponent } from 'app/shared/tables/ingredient-table/ingredient-table.component';

@Component({
  selector: 'jhi-ingredient',
  templateUrl: './ingredient.component.html',
})
export class IngredientComponent implements OnInit, OnDestroy {
  ingredients?: IIngredient[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = 20;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 0;
  tableLoaded = false;
  @ViewChild('tableComponent', { static: false }) table!: IngredientTableComponent;

  constructor(
    protected ingredientService: IngredientService,
    protected activatedRoute: ActivatedRoute,
    protected dataUtils: JhiDataUtils,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  private loadPage(page?: number): void {
    const pageToLoad: number = page || this.page || 0;
    if (this.totalItems === 0) {
      this.ingredientService
        .queryAll({
          // page: pageToLoad - 1,
          // size: 439,
          sort: this.sort(),
        })
        .subscribe(
          (res: HttpResponse<IIngredient[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
          () => this.onError()
        );
    } else {
      this.refresh(pageToLoad);
    }
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

  ngOnInit(): void {
    this.handleNavigation();
    this.registerChangeInIngredients();
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

  trackId(index: number, item: IIngredient): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInIngredients(): void {
    // this.eventSubscriber = this.eventManager
    //   .subscribe(
    //     'ingredientListModification',
    //     () => this.loadPage());
  }

  delete(ingredient: IIngredient): void {
    const modalRef = this.modalService.open(IngredientDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.ingredient = ingredient;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IIngredient[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.ingredients = data || [];
    this.ngbPaginationPage = this.page;
    this.tableLoaded = true;
  }

  protected refresh(page: number): void {
    this.page = page;
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page ?? 1;
  }

  public navigate(): void {
    this.router.navigate(['/ingredient'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc'),
      },
    });
  }
}

import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data, ParamMap, Router } from '@angular/router';
import { combineLatest, Subscription } from 'rxjs';
import { JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IIngredient } from 'app/shared/model/ingredient.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { IngredientService } from './ingredient.service';
import { IngredientDeleteDialogComponent } from './ingredient-delete-dialog.component';
import { Status } from 'app/shared/model/enumerations/status.model';
import { IngredientTableComponent } from 'app/shared/tables/ingredient-table/ingredient-table.component';
import { MatDialog } from '@angular/material/dialog';
import { UnitDetailComponent } from 'app/entities/unit/unit-detail.component';
import { IngredientDetailComponent } from 'app/entities/ingredient/ingredient-detail.component';

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
  requesting = false;
  @ViewChild('tbl', { static: false }) tbl!: IngredientTableComponent;

  constructor(
    protected ingredientService: IngredientService,
    protected activatedRoute: ActivatedRoute,
    protected dataUtils: JhiDataUtils,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.handleNavigation();
    this.registerChangeInIngredients();
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
    this.eventSubscriber = this.eventManager.subscribe('ingredientListModification', () => this.loadPage());
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
    this.router.navigate(['ingredient'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc'),
      },
    });
  }

  setStatus(element: IIngredient, newStatus: boolean): void {
    this.ingredientService
      .update({
        ...element,
        status: !newStatus ? (Status.ACTIVE.toUpperCase() as Status) : (Status.INACTIVE.toUpperCase() as Status),
      })
      .subscribe(() => {
        this.loadPage(this.page);
      });
  }

  view(element: any): void {
    this.dialog.open(IngredientDetailComponent, {
      maxWidth: '600px',
      maxHeight: '90%',
      width: '60%',
      data: element,
    });
  }

  viewType(id: number): void {
    this.dialog.open(UnitDetailComponent, {
      data: id,
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

  protected onSuccess(data: IIngredient[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = this.ingredients?.length ?? 0;
    this.page = page;
    this.ingredients = data || [];
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
    this.tbl.reloadSource(this.ingredients as IIngredient[]);
    this.navigate();
  }

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page || 0;
    this.requesting = true;
    this.ingredientService
      .queryAll({
        sort: this.sort(),
      })
      .subscribe(
        (res: HttpResponse<IIngredient[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
        () => this.onError()
      );
  }
}

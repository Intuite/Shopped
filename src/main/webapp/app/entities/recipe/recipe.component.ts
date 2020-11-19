import { Component, OnInit, OnDestroy, ViewChild } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, ParamMap, Router, Data } from '@angular/router';
import { Subscription, combineLatest } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IRecipe } from 'app/shared/model/recipe.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { RecipeService } from './recipe.service';
import { RecipeDeleteDialogComponent } from './recipe-delete-dialog.component';
import { Status } from 'app/shared/model/enumerations/status.model';
import { RecipeTableComponent } from 'app/shared/tables/recipe-table/recipe-table.component';

@Component({
  selector: 'jhi-recipe',
  templateUrl: './recipe.component.html',
})
export class RecipeComponent implements OnInit, OnDestroy {
  recipes?: IRecipe[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;
  tableLoaded = false;
  requesting = false;
  @ViewChild('table', { static: false }) table!: RecipeTableComponent;

  constructor(
    protected recipeService: RecipeService,
    protected activatedRoute: ActivatedRoute,
    protected dataUtils: JhiDataUtils,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  ngOnInit(): void {
    this.handleNavigation();
    this.registerChangeInRecipes();
  }

  ngOnDestroy(): void {
    //  if (this.eventSubscriber) {
    //    this.eventManager.destroy(this.eventSubscriber);
    //  }
  }

  trackId(index: number, item: IRecipe): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInRecipes(): void {
    // this.eventSubscriber = this.eventManager.subscribe('recipeListModification', () => this.loadPage());
  }

  delete(recipe: IRecipe): void {
    const modalRef = this.modalService.open(RecipeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.recipe = recipe;
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
    this.router.navigate(['./recipe'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc'),
      },
    });
  }

  setStatus(element: IRecipe, newStatus: boolean): void {
    this.recipeService
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

  protected onSuccess(data: IRecipe[] | null, headers: HttpHeaders, page: number): void {
    this.page = page;
    this.recipes = data || [];
    this.totalItems = this.recipes?.length ?? 0;
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
    this.table.reloadSource(this.recipes as IRecipe[]);
    this.navigate();
  }

  private loadPage(page?: number): void {
    const pageToLoad: number = page || this.page || 0;
    this.requesting = true;
    this.recipeService
      .queryAll({
        sort: this.sort(),
      })
      .subscribe(
        (res: HttpResponse<IRecipe[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
        () => this.onError()
      );
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }
}

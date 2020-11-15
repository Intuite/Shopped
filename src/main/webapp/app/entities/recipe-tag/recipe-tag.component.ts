import { Component, OnDestroy, OnInit } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data, ParamMap, Router } from '@angular/router';
import { combineLatest, Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IRecipeTag } from 'app/shared/model/recipe-tag.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { RecipeTagService } from './recipe-tag.service';
import { RecipeTagDeleteDialogComponent } from './recipe-tag-delete-dialog.component';
import { IIngredient } from 'app/shared/model/ingredient.model';
import { Status } from 'app/shared/model/enumerations/status.model';
import { MatDialog } from '@angular/material/dialog';
import { RecipeTagDetailComponent } from 'app/entities/recipe-tag/recipe-tag-detail.component';
import { TagTypeDetailComponent } from 'app/entities/tag-type/tag-type-detail.component';

@Component({
  selector: 'jhi-recipe-tag',
  templateUrl: './recipe-tag.component.html',
})
export class RecipeTagComponent implements OnInit, OnDestroy {
  recipeTags?: IRecipeTag[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;
  tableLoaded = false;

  constructor(
    protected recipeTagService: RecipeTagService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    public dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.handleNavigation();
    this.registerChangeInRecipeTags();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IRecipeTag): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInRecipeTags(): void {
    this.eventSubscriber = this.eventManager.subscribe('recipeTagListModification', () => this.loadPage());
  }

  delete(recipeTag: IRecipeTag): void {
    const modalRef = this.modalService.open(RecipeTagDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.recipeTag = recipeTag;
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
    this.router.navigate(['/recipe-tag'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc'),
      },
    });
  }

  setStatus(element: IIngredient, newStatus: boolean): void {
    this.recipeTagService
      .update({
        ...element,
        status: !newStatus ? (Status.ACTIVE.toUpperCase() as Status) : (Status.INACTIVE.toUpperCase() as Status),
      })
      .subscribe(() => {
        this.loadPage(this.page);
      });
  }

  view(recipeTag: any): void {
    this.dialog.open(RecipeTagDetailComponent, {
      data: recipeTag,
    });
  }

  viewType(typeId: number): void {
    this.dialog.open(TagTypeDetailComponent, {
      data: typeId,
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
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.recipeTags = data || [];
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
      this.recipeTagService
        .queryAll({
          // page: pageToLoad - 1,
          // size: 439,
          sort: this.sort(),
        })
        .subscribe(
          (res: HttpResponse<IRecipeTag[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
          () => this.onError()
        );
    } else {
      this.refresh(pageToLoad);
    }
  }
}

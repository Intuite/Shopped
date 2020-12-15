import { Component, OnInit, OnDestroy, ViewChild } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, ParamMap, Router, Data } from '@angular/router';
import { Subscription, combineLatest } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICollectionHasRecipe } from 'app/shared/model/collection-has-recipe.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { CollectionHasRecipeDeleteDialogComponent } from './collection-has-recipe-delete-dialog.component';
import { PageEvent } from '@angular/material/paginator';
import { IRecipe } from 'app/shared/model/recipe.model';
import { RecipeService } from 'app/entities/recipe/recipe.service';
import { CollectionHasRecipeService } from 'app/entities/collection-has-recipe/collection-has-recipe.service';
import { ICollection } from 'app/shared/model/collection.model';
import { RecipeTagPickerComponent } from 'app/shared/components/pickers/recipe-tag-picker/recipe-tag-picker.component';
import { IRecipeTag } from 'app/shared/model/recipe-tag.model';

@Component({
  selector: 'jhi-collection-has-recipe',
  templateUrl: './collection-has-recipe.component.html',
  styleUrls: ['./collection-has-recipe.scss'],
})
export class CollectionHasRecipeComponent implements OnInit, OnDestroy {
  collection?: ICollection;
  collectionHasRecipes?: ICollectionHasRecipe[] | null;
  recipes?: IRecipe[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;
  requesting = false;
  pageEvent!: PageEvent;
  temporalEntry?: ICollectionHasRecipe;
  searchText = '';
  dataLoaded = false;
  @ViewChild('recipeTagPk') recipePiker!: RecipeTagPickerComponent;
  recipeTags: IRecipeTag[] = [];

  constructor(
    protected collectionHasRecipeService: CollectionHasRecipeService,
    protected recipeService: RecipeService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    private activeModal: NgbActiveModal
  ) {}

  loadAll(): void {
    if (this.collection?.id) this.retrieveCollectionHasRecipes(this.collection.id);
  }

  ngOnInit(): void {
    this.handleNavigation();
    this.registerChangeInCollectionHasRecipes();
    this.loadAll();
  }

  retrieveRecipes(collectionHasRecipesIds: number[]): any {
    this.recipeService
      .query({
        ...{ 'id.in': collectionHasRecipesIds },
      })
      .subscribe(
        (res: HttpResponse<IRecipe[]>) => this.onSuccess(res.body, res.headers),
        () => this.onError()
      );
  }

  retrieveCollectionHasRecipe(collectionId: number, recipeId: number): void {
    this.collectionHasRecipeService
      .queryAll({
        ...{ 'collectionId.equals': collectionId },
        ...{ 'recipeId.equals': recipeId },
      })
      .subscribe(
        (res: HttpResponse<ICollectionHasRecipe[]>) => {
          this.collectionHasRecipes = res.body || null;
        },
        () => {}
      );
  }

  retrieveCollectionHasRecipes(collectionId: number): void {
    this.requesting = true;
    this.collectionHasRecipeService
      .queryAll({
        ...{ 'collectionId.equals': collectionId },
      })
      .subscribe(
        (res: HttpResponse<ICollectionHasRecipe[]>) => {
          if (res.body) {
            this.temporalEntry = res.body[0] || null;
            this.retrieveRecipes(this.getRecipesIds(res.body));
          }
        },
        () => {
          this.requesting = false;
        }
      );
  }

  getRecipesIds(entries: ICollectionHasRecipe[]): number[] {
    const recipeIds: number[] = [];
    for (const entry of entries) {
      if (entry?.id)
        if (entry.recipeId != null) {
          recipeIds?.push(entry.recipeId);
        }
    }
    return recipeIds;
  }

  protected handleNavigation(): void {
    combineLatest(this.activatedRoute.data, this.activatedRoute.queryParamMap, (data: Data, params: ParamMap) => {
      const page = params.get('page');
      this.page = page !== null ? +page : 1;
      const pageSize = params.get('size');
      this.itemsPerPage = pageSize !== null ? +pageSize : ITEMS_PER_PAGE;
      const sort = (params.get('sort') ?? data['defaultSort']).split(',');
      this.predicate = sort[0];
      this.ascending = sort[1] === 'asc';
      this.loadAll();
    }).subscribe();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICollectionHasRecipe): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCollectionHasRecipes(): void {
    this.eventSubscriber = this.eventManager.subscribe('collectionHasRecipeListModification', () => {
      this.loadAll();
    });
  }

  // create(): void {
  //   const modalRef = this.modalService.open(CollectionHasRecipeUpdateComponent, { size: 'lg', backdrop: 'static', centered: true });
  //   modalRef.componentInstance.currentCollection = this.collection;
  // }

  delete(recipe: IRecipe): void {
    this.collectionHasRecipeService
      .queryAll({
        ...{ 'collectionId.equals': this.collection?.id },
        ...{ 'recipeId.equals': recipe.id },
      })
      .subscribe(
        (res: HttpResponse<ICollectionHasRecipe[]>) => {
          if (res.body) {
            const tempEntry = res.body[0] || null;
            const modalRef = this.modalService.open(CollectionHasRecipeDeleteDialogComponent, {
              size: 'lg',
              backdrop: 'static',
              centered: true,
            });
            modalRef.componentInstance.collectionHasRecipe = tempEntry;
          }
        },
        () => {}
      );
  }

  // sort(): string[] {
  //   const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
  //   if (this.predicate !== 'id') {
  //     result.push('id');
  //   }
  //   return result;
  // }

  protected onSuccess(data: IRecipe[] | null, headers: HttpHeaders): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.recipes = data || [];
    this.ngbPaginationPage = this.page;
    this.requesting = false;
    this.dataLoaded = true;
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page ?? 1;
    this.requesting = false;
  }

  close(): void {
    this.activeModal.close();
  }

  cancel(): void {
    this.activeModal.dismiss();
  }

  setRecipeTags(): void {
    this.recipeTags = this.recipePiker.getRecipeTags();
  }
}

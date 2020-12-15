import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, ParamMap, Router, Data } from '@angular/router';
import { Subscription, combineLatest } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICollection } from 'app/shared/model/collection.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { CollectionService } from './collection.service';
import { CollectionDeleteDialogComponent } from './collection-delete-dialog.component';
import { PageEvent } from '@angular/material/paginator';
import { Account } from 'app/core/user/account.model';
import { AccountService } from 'app/core/auth/account.service';
import { CollectionUpdateComponent } from 'app/entities/collection/collection-update.component';
import { CollectionHasRecipeComponent } from 'app/entities/collection-has-recipe/collection-has-recipe.component';

@Component({
  selector: 'jhi-collection',
  templateUrl: './collection.component.html',
  styleUrls: ['./collection.scss'],
})
export class CollectionComponent implements OnInit, OnDestroy {
  collections?: ICollection[] | null = null;
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = 4;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;
  requesting = false;
  pageEvent!: PageEvent;
  currentAccount: Account | null = null;
  dataLoaded = false;
  searchText = '';
  orderAsc = false;

  constructor(
    protected collectionService: CollectionService,
    private accountService: AccountService,
    protected activatedRoute: ActivatedRoute,
    protected dataUtils: JhiDataUtils,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  ngOnInit(): void {
    this.accountService.identity().subscribe(account => (this.currentAccount = account));
    this.handleNavigation();
    this.registerChangeInCollections();
  }

  registerChangeInCollections(): void {
    this.eventSubscriber = this.eventManager.subscribe('collectionListModification', () => this.loadAll());
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

  loadAll(): void {
    this.requesting = true;
    this.collectionService
      .queryAll({
        ...{ 'userId.equals': this.currentAccount?.id },
      })
      .subscribe(
        (res: HttpResponse<ICollection[]>) => {
          this.onSuccess(res.body, res.headers);
        },
        () => this.onError()
      );
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICollection): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  transition(): void {
    this.router.navigate(['./'], {
      relativeTo: this.activatedRoute.parent,
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc'),
      },
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  create(): void {
    const modalRef = this.modalService.open(CollectionUpdateComponent, { size: 'lg', backdrop: 'static', centered: true });
    modalRef.componentInstance.currentAccount = this.currentAccount;
  }

  edit(collection: ICollection): void {
    const modalRef = this.modalService.open(CollectionUpdateComponent, { size: 'lg', backdrop: 'static', centered: true });
    modalRef.componentInstance.collection = collection;
    modalRef.componentInstance.currentAccount = this.currentAccount;
  }

  delete(collection: ICollection): void {
    const modalRef = this.modalService.open(CollectionDeleteDialogComponent, { size: 'lg', backdrop: 'static', centered: true });
    modalRef.componentInstance.collection = collection;
  }

  view(collection: ICollection): void {
    const modalRef = this.modalService.open(CollectionHasRecipeComponent, { size: 'xl', backdrop: 'static', centered: true });
    modalRef.componentInstance.collection = collection;
  }

  // sort(): string[] {
  //   const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
  //   if (this.predicate !== 'id') {
  //     result.push('id');
  //   }
  //   return result;
  // }

  protected onSuccess(data: ICollection[] | null, headers: HttpHeaders): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.collections = data;
    this.dataLoaded = true;
    this.requesting = false;
  }

  protected onError(): void {
    this.requesting = false;
  }
}

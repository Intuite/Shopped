import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IBundle } from 'app/shared/model/bundle.model';
import { BundleService } from './bundle.service';
import { BundleDeleteDialogComponent } from './bundle-delete-dialog.component';
@Component({
  selector: 'jhi-bundle',
  templateUrl: './bundle.component.html',
})
export class BundleComponent implements OnInit, OnDestroy {
  bundles?: IBundle[];
  eventSubscriber?: Subscription;

  constructor(protected bundleService: BundleService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.bundleService.query().subscribe((res: HttpResponse<IBundle[]>) => (this.bundles = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInBundles();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IBundle): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInBundles(): void {
    this.eventSubscriber = this.eventManager.subscribe('bundleListModification', () => this.loadAll());
  }

  delete(bundle: IBundle): void {
    const modalRef = this.modalService.open(BundleDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.bundle = bundle;
  }
}

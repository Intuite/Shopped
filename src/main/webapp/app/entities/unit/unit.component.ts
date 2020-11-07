import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IUnit } from 'app/shared/model/unit.model';
import { UnitService } from './unit.service';
import { UnitDeleteDialogComponent } from './unit-delete-dialog.component';

@Component({
  selector: 'jhi-unit',
  templateUrl: './unit.component.html',
})
export class UnitComponent implements OnInit, OnDestroy {
  units?: IUnit[];
  eventSubscriber?: Subscription;

  constructor(protected unitService: UnitService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.unitService.query().subscribe((res: HttpResponse<IUnit[]>) => (this.units = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInUnits();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IUnit): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInUnits(): void {
    this.eventSubscriber = this.eventManager.subscribe('unitListModification', () => this.loadAll());
  }

  delete(unit: IUnit): void {
    const modalRef = this.modalService.open(UnitDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.unit = unit;
  }
}

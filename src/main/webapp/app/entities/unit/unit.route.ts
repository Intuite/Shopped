import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { IUnit, Unit } from 'app/shared/model/unit.model';
import { UnitService } from './unit.service';

@Injectable({ providedIn: 'root' })
export class UnitResolve implements Resolve<IUnit> {
  constructor(private service: UnitService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUnit> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((unit: HttpResponse<Unit>) => {
          if (unit.body) {
            return of(unit.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Unit());
  }
}

export const unitRoute: Routes = [
  // {
  // path: '',
  // component: UnitComponent,
  // data: {
  // authorities: [Authority.USER],
  // pageTitle: 'shoppedApp.unit.home.title',
  // },
  // canActivate: [UserRouteAccessService],
  // },
  // {
  // path: ':id/view',
  // component: UnitDetailComponent,
  // resolve: {
  // unit: UnitResolve,
  // },
  // data: {
  // authorities: [Authority.USER],
  // pageTitle: 'shoppedApp.unit.home.title',
  // },
  // canActivate: [UserRouteAccessService],
  // },
  // {
  // path: 'new',
  // component: UnitUpdateComponent,
  // resolve: {
  // unit: UnitResolve,
  // },
  // data: {
  // authorities: [Authority.USER],
  // pageTitle: 'shoppedApp.unit.home.title',
  // },
  // canActivate: [UserRouteAccessService],
  // },
  // {
  // path: ':id/edit',
  // component: UnitUpdateComponent,
  // resolve: {
  // unit: UnitResolve,
  // },
  // data: {
  // authorities: [Authority.USER],
  // pageTitle: 'shoppedApp.unit.home.title',
  // },
  // canActivate: [UserRouteAccessService],
  // },
];

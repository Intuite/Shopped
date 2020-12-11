import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { JhiEventManager } from 'ng-jhipster';

import { IBite } from 'app/shared/model/bite.model';
import { BiteService } from 'app/entities/bite/bite.service';
import { Status } from 'app/shared/model/enumerations/status.model';

interface TopItem {
  id?: number;
  status?: Status;
  recipeName?: string;
  recipeId?: number;
  userLogin?: string;
  userId?: number;
}

@Component({
  selector: 'jhi-tops',
  templateUrl: './tops.component.html',
  styleUrls: ['./tops.component.scss'],
})
export class TopsComponent implements OnInit {
  bites?: IBite[];
  tops?: {} | any;

  constructor(
    protected biteService: BiteService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager
  ) {}

  ngOnInit(): void {
    this.biteService.query().subscribe(
      (res: HttpResponse<IBite[]>) => this.onSuccessPost(res.body),
      () => this.onError()
    );
  }

  private onError(): void {
    console.warn('There was an error');
  }

  private onSuccessPost(body: IBite[] | null): void {
    this.bites = body || [];
    this.countBites(body);
  }

  countBites(bites: IBite[] | null): void {
    if (bites !== null) {
      for (let i = 0; bites.length; i++) {
        const num: any = bites[i].postId;
        // count[num] = count[num] ? count[num] +1 :1;
        this.tops[num] = this.tops[num] ? this.tops[num] + 1 : 1;
      }
    }
  }
}

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICommendation } from 'app/shared/model/commendation.model';

@Component({
  selector: 'jhi-commendation-detail',
  templateUrl: './commendation-detail.component.html',
})
export class CommendationDetailComponent implements OnInit {
  commendation: ICommendation | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ commendation }) => (this.commendation = commendation));
  }

  previousState(): void {
    window.history.back();
  }
}

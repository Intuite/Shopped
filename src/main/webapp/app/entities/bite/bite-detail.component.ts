import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBite } from 'app/shared/model/bite.model';

@Component({
  selector: 'jhi-bite-detail',
  templateUrl: './bite-detail.component.html',
})
export class BiteDetailComponent implements OnInit {
  bite: IBite | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bite }) => (this.bite = bite));
  }

  previousState(): void {
    window.history.back();
  }
}

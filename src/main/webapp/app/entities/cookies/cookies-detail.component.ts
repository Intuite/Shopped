import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICookies } from 'app/shared/model/cookies.model';

@Component({
  selector: 'jhi-cookies-detail',
  templateUrl: './cookies-detail.component.html',
})
export class CookiesDetailComponent implements OnInit {
  cookies: ICookies | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cookies }) => (this.cookies = cookies));
  }

  previousState(): void {
    window.history.back();
  }
}

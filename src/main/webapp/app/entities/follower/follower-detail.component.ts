import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFollower } from 'app/shared/model/follower.model';

@Component({
  selector: 'jhi-follower-detail',
  templateUrl: './follower-detail.component.html',
})
export class FollowerDetailComponent implements OnInit {
  follower: IFollower | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ follower }) => (this.follower = follower));
  }

  previousState(): void {
    window.history.back();
  }
}

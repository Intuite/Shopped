import { Component, Input, OnInit } from '@angular/core';
import { Account } from 'app/core/user/account.model';
import { User } from 'app/core/user/user.model';

@Component({
  selector: 'jhi-public-dashboard',
  templateUrl: './public-dashboard.component.html',
  styleUrls: ['./public-dashboard.component.scss'],
})
export class PublicDashboardComponent implements OnInit {
  @Input() account!: Account | null;
  @Input() user!: User | null;

  constructor() {}

  ngOnInit(): void {}
}

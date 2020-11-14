import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'jhi-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.scss'],
})
export class ListComponent implements OnInit {
  // tslint:disable-next-line:no-input-rename
  @Input() data: any[] | undefined;

  constructor() {}

  ngOnInit(): void {
    console.warn(this.data);
  }
}

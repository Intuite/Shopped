import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'jhi-grid-layout',
  templateUrl: './grid-layout.component.html',
  styleUrls: ['./grid-layout.component.scss'],
})
export class GridLayoutComponent implements OnInit {
  @Input() data: any[] | undefined;

  constructor() {}

  ngOnInit(): void {
    console.warn(this.data);
  }
}
